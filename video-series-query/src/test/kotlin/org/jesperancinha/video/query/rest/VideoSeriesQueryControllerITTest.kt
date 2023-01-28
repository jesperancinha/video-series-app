package org.jesperancinha.video.query.rest

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.bson.Document
import org.jesperancinha.video.core.data.Genre.*
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
import org.springframework.test.context.ContextConfiguration
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
@ContextConfiguration(initializers = [VideoSeriesQueryInitializer::class])
class VideoSeriesQueryControllerITTest(
    @Autowired private val testRestTemplate: TestRestTemplate,
    @Autowired private val videoSeriesRepository: VideoSeriesRepository,
    @Autowired private val mongoClient: MongoClient
) : WordSpec(
    {

        class VideoSeriesList : MutableList<VideoSeries> by ArrayList()

        "should receive data and respond correctly" should {
            "return initial list when no data has been inserted" {
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
                    name="Halloween",
                    volumes = 6,
                    cashValue = BigDecimal.valueOf(1_000_000),
                    genre = HORROR
                )

                val responseCreateEntity =
                    testRestTemplate.restTemplate.postForEntity<Any>(
                        "http://${vsaContainer.host}:${vsaContainer.firstMappedPort}/video-series",
                        film
                    )

                responseCreateEntity.statusCode shouldBe OK

                val resultingDocumentList = mongoClient
                    .getDatabase("axonframework")
                    .getCollection("domainevents")
                    .find()
                resultingDocumentList.toList()
                    .shouldHaveSize(1)
                val filmOnEventQueue: VideoSeriesDto = resultingDocumentList.findFirstDocumentInCollection()
                filmOnEventQueue.id.shouldNotBeNull()
                filmOnEventQueue.name shouldBe "Nightmare on Elm Street I"
                filmOnEventQueue.genre shouldBe HORROR
                filmOnEventQueue.cashValue shouldBe BigDecimal.valueOf(1000000)
                filmOnEventQueue.volumes shouldBe 1

                delay(5000)
                val responseResultEntity =
                    testRestTemplate.getForEntity<VideoSeriesList>("/video-series", VideoSeriesList::class)

                responseResultEntity.shouldNotBeNull()
                val allVSAResults = responseResultEntity.body as List<*>
                allVSAResults.shouldNotBeEmpty()
                allVSAResults shouldHaveSize 4
            }
        }

    }
) {
    companion object {

        @JvmField
        var network: Network = Network.newNetwork()

        @Container
        @JvmField
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5")
            .withNetwork(network)
            .withNetworkAliases("mongo")
            .withExposedPorts(27017)

        @Container
        @JvmStatic
        val vsaContainer: GenericContainer<*> = GenericContainer(
            ImageFromDockerfile("vsa-test-image")
                .withFileFromClasspath("entrypoint.sh", "/entrypoint.sh")
                .withFileFromClasspath(
                    "video-series-command.jar",
                    "/video-series-command-2.0.0.jar"
                )
                .withDockerfileFromBuilder { builder: DockerfileBuilder ->
                    builder
                        .from("adoptopenjdk/openjdk16")
                        .workDir("/usr/local/bin/")
                        .run("apt-get update")
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

        @Container
        @JvmStatic
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>("postgres").apply {
            withNetwork(network)
            withNetworkAliases("postgres")
            withDatabaseName("vsa")
            withUsername("postgres")
            withPassword("admin")
            withExposedPorts(5432)
        }

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
            registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort)
            registry.add("spring.data.mongodb.host", mongoDBContainer::getHost)
            registry.add("spring.data.mongodb.database") { "axonframework" }
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
        }

        init {
            mongoDBContainer.start()
            postgreSQLContainer.start()
            vsaContainer.start()
        }

    }

    override fun extensions(): List<io.kotest.core.extensions.Extension> = listOf(SpringExtension)

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
