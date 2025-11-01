package org.jesperancinha.video.query.rest

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.*
import org.bson.Document
import org.jesperancinha.video.core.data.Genre.HORROR
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.jesperancinha.video.query.data.VideoSeries
import org.jesperancinha.video.query.jpa.VideoSeriesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.client.postForEntity
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.images.builder.ImageFromDockerfile
import org.testcontainers.images.builder.dockerfile.DockerfileBuilder
import org.testcontainers.junit.jupiter.Container
import java.math.BigDecimal

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("local")
class VideoSeriesQueryControllerITTest(
    @Autowired private val testRestTemplate: TestRestTemplate,
    @Autowired private val videoSeriesRepository: VideoSeriesRepository,
    @Autowired private val mongoClient: MongoClient
) : WordSpec(
    {

        class VideoSeriesList : MutableList<VideoSeries> by ArrayList()

        "should receive data and respond correctly" should {
            "return initial list when no data has been inserted" {
                runBlocking(Dispatchers.IO) {
                    val responseEntity =
                        testRestTemplate.getForEntity<VideoSeriesList>("/video-series", VideoSeriesList::class)

                    responseEntity.shouldNotBeNull()
                    val allVSAs = responseEntity.body as List<*>
                    allVSAs.shouldHaveSize(3)

                    mongoClient
                        .getDatabase("axonframework")
                        .getCollection("domainevents")
                        .find().toList()
                        .shouldBeEmpty()

                    val film = VideoSeriesDto(
                        name = "Halloween",
                        volumes = 6,
                        cashValue = BigDecimal.valueOf(1_000_000),
                        genre = HORROR
                    )

                    launch {
                        testRestTemplate.restTemplate.postForEntity<Any>(
                            "http://${vsaContainer.host}:${vsaContainer.getMappedPort(8080)}/video-series",
                            film
                        ).statusCode shouldBe OK
                    }.join()

                    val resultingDocumentList = mongoClient
                        .getDatabase("axonframework")
                        .getCollection("domainevents")
                        .find()
                    resultingDocumentList.toList()
                        .shouldHaveSize(1)
                    val filmOnEventQueue: VideoSeriesDto = resultingDocumentList.findFirstDocumentInCollection()
                    filmOnEventQueue.id.shouldNotBeNull()
                    filmOnEventQueue.name shouldBe "Halloween"
                    filmOnEventQueue.genre shouldBe HORROR
                    filmOnEventQueue.cashValue shouldBe BigDecimal.valueOf(1000000)
                    filmOnEventQueue.volumes shouldBe 6

                    repeat(4) {
                        delay(1000)
                        val responseResultEntity =
                            testRestTemplate.getForEntity<VideoSeriesList>("/video-series", VideoSeriesList::class)
                        responseResultEntity.shouldNotBeNull()

                    }
                    val responseResultEntity =
                        testRestTemplate.getForEntity<VideoSeriesList>("/video-series", VideoSeriesList::class)
                    responseResultEntity.shouldNotBeNull()
                    val allVSAResults = responseResultEntity.body as List<*>
                    allVSAResults.shouldNotBeEmpty()
                    allVSAResults shouldHaveSize 4
                }
            }
        }

    }
) {
    companion object {

        @JvmField
        var network: Network = Network.newNetwork()

        @Container
        @JvmField
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo")
            .withNetwork(network)
            .withNetworkAliases("mongo")
            .withExposedPorts(27017)
            .also { it.start() }

        @Container
        @JvmStatic
        val vsaContainer: GenericContainer<*> = GenericContainer(
            ImageFromDockerfile("vsa-test-image")
                .withFileFromClasspath("entrypoint.sh", "/entrypoint.sh")
                .withFileFromClasspath(
                    "video-series-command.jar",
                    "/video-series-command.jar"
                )
                .withDockerfileFromBuilder { builder: DockerfileBuilder ->
                    builder
                        .from("eclipse-temurin:21-alpine")
                        .workDir("/usr/local/bin/")
                        .copy(
                            "video-series-command.jar",
                            "/usr/local/bin/video-series-command.jar"
                        )
                        .copy("entrypoint.sh", "/usr/local/bin/entrypoint.sh")
                        .run("chmod +x /usr/local/bin/entrypoint.sh")
                        .expose(8080)
                        .entryPoint("entrypoint.sh")
                        .build()
                })
            .withExposedPorts(8080)
            .withNetwork(network)
            .also { it.start() }

        @Container
        @JvmStatic
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>("postgres:15.0-alpine").apply {
            withNetwork(network)
            withNetworkAliases("postgres")
            withDatabaseName("vsa")
            withUsername("postgres")
            withPassword("admin")
            withExposedPorts(5432)
        }.also { it.start() }

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort)
            registry.add("video.series.mongo.port", mongoDBContainer::getFirstMappedPort)
            registry.add("spring.data.mongodb.host", mongoDBContainer::getHost)
            registry.add("spring.data.mongodb.database") { "axonframework" }
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
        }
    }

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)
        mongoDBContainer.isRunning.shouldBeTrue()
        postgreSQLContainer.isRunning.shouldBeTrue()
        vsaContainer.isRunning.shouldBeTrue()
    }

    override suspend fun afterEach(testCase: TestCase, result: io.kotest.core.test.TestResult) {
        super.afterEach(testCase, result)
        withContext(Dispatchers.IO) {
            videoSeriesRepository.deleteAll()
        }
    }

}

private fun <TResult : Document> FindIterable<TResult>.findFirstDocumentInCollection(): VideoSeriesDto =
    XmlMapper().readValue(first()["serializedPayload"].toString(), VideoSeriesDto::class.java)
