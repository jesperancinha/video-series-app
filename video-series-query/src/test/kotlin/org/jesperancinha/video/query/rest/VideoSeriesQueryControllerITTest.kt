package org.jesperancinha.video.query.rest

import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.spring.SpringListener
import kotlinx.coroutines.delay
import org.jesperancinha.video.core.data.Genre
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.jesperancinha.video.query.data.VideoSeries
import org.jesperancinha.video.query.jpa.VideoSeriesRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
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
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal


@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("postgres")
class VideoSeriesQueryControllerITTest(
    private val testRestTemplate: TestRestTemplate,
    private val videoSeriesRepository: VideoSeriesRepository,
    private val mongoTemplate: MongoTemplate,
) : WordSpec(
    {

        class VideoSeriesList : MutableList<VideoSeries> by ArrayList()

        "should receive data and respond correctly" should {
            "return initial list when no data has been inserted" {
                val responseEntity =
                    testRestTemplate.getForEntity<VideoSeriesList>("/video-series", VideoSeriesList::class)

                responseEntity.shouldNotBeNull()
                val allVSAs = responseEntity.body as List<*>
                allVSAs.shouldBeEmpty()

                val allDomainEvents = mongoTemplate
                    .find(Query.query(Criteria()), Any::class.java, "domainevents")

                allDomainEvents shouldHaveSize 0

                val film = VideoSeriesDto.builder()
                    .name("Halloween")
                    .cashValue(BigDecimal.valueOf(1_000_000))
                    .genre(Genre.HORROR)
                    .build()

                val responseCreateEntity =
                    testRestTemplate.restTemplate.postForEntity<Any>("http://localhost:${vsaContainer.firstMappedPort}/video-series",
                        film)

                responseCreateEntity.statusCode shouldBe HttpStatus.OK
                val allPostDomainEvents = mongoTemplate
                    .find(Query.query(Criteria()), LinkedHashMap::class.java, "domainevents")

                allPostDomainEvents shouldHaveSize 1

                val filmOnEventQueue = allPostDomainEvents[0]["serializedPayload"].toString()

                filmOnEventQueue shouldContain "Halloween"
                filmOnEventQueue shouldContain "HORROR"
                filmOnEventQueue shouldContain "1000000"

                delay(5000)

                val responseResultEntity =
                    testRestTemplate.getForEntity<VideoSeriesList>("/video-series", VideoSeriesList::class)

                responseResultEntity.shouldNotBeNull()
                val allVSAResults = responseResultEntity.body as List<*>
                allVSAResults.shouldNotBeEmpty()
                allVSAResults shouldHaveSize 1
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
        val vsaContainer: GenericContainer<*> = GenericContainer<GenericContainer<*>>(
            ImageFromDockerfile("vsa-test-image")
                .withFileFromClasspath("entrypoint.sh", "/entrypoint.sh")
                .withFileFromClasspath("video-series-command.jar",
                    "/video-series-command-0.0.1-SNAPSHOT.jar")
                .withDockerfileFromBuilder { builder: DockerfileBuilder ->
                    builder
                        .from("adoptopenjdk/openjdk16")
                        .workDir("/usr/local/bin/")
                        .run("apt-get update")
                        .copy("video-series-command.jar",
                            "/usr/local/bin/video-series-command.jar")
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

    override fun listeners(): List<TestListener> = listOf(SpringListener)

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)
        mongoDBContainer.isRunning.shouldBeTrue()
        postgreSQLContainer.isRunning.shouldBeTrue()
        vsaContainer.isRunning.shouldBeTrue()
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        super.afterEach(testCase, result)
        videoSeriesRepository.deleteAll()
    }
}
