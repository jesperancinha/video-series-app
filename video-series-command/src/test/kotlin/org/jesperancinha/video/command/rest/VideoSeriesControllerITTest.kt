package org.jesperancinha.video.command.rest

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import org.jesperancinha.video.core.data.Genre
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.postForEntity
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait.forListeningPort
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = [VideoSeriesControllerITTest.VideoSeriesCommandInitializer::class])
class VideoSeriesControllerITTest(
    @Autowired
    private val jdbcTemplate: JdbcTemplate,
    @Autowired
    private val testRestTemplate: TestRestTemplate,
) : WordSpec(
    {
        "should receive data and respond correctly" should {
            "should send a video title" {
//                val allDomainEvents = jdbcTemplate
//                    .query(Query.query(Criteria()), Any::class.java, "domainevents")
//
//                allDomainEvents shouldHaveSize 0

                val film = VideoSeriesDto.builder()
                    .name("Nightmare on Elm Street I")
                    .cashValue(BigDecimal.valueOf(1_000_000))
                    .genre(Genre.HORROR)
                    .build()

                val responseEntity = testRestTemplate.restTemplate.postForEntity<VideoSeriesDto>("/video-series", film)
//
//                responseEntity.statusCode shouldBe HttpStatus.OK
//                val allPostDomainEvents = jdbcTemplate
//                    .find(Query.query(Criteria()), LinkedHashMap::class.java, "domainevents")
//
//                allPostDomainEvents shouldHaveSize 1
//
//                val filmOnEventQueue = allPostDomainEvents[0]["serializedPayload"].toString()
//
//                filmOnEventQueue shouldContain "Nightmare on Elm Street I"
//                filmOnEventQueue shouldContain "HORROR"
//                filmOnEventQueue shouldContain "1000000"
            }
        }
    }
) {

//    companion object {
//        @Container
//        @JvmField
//        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5")
//
//        init {
//            mongoDBContainer.start()
//        }
//
//        @DynamicPropertySource
//        @JvmStatic
//        fun setProperties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.data.mongodb.uri", mongoDBContainer::getConnectionString)
//            registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort)
//            registry.add("spring.data.mongodb.host", mongoDBContainer::getHost)
//            registry.add("spring.data.mongodb.database") { "cqrs" }
//        }
//    }

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)
//        mongoDBContainer.isRunning.shouldBeTrue()

    }

    class VideoSeriesCommandInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        private val dockerCompose by lazy {
            DockerCompose(listOf(File("../docker-compose-db.yml")))
                .withExposedService("postgres_1", 5432, forListeningPort())
                .withExposedService("postgres-es_1", 5432, forListeningPort())
                .withLocalCompose(true)
                .also { it.start() }
        }

        override fun initialize(applicationContextx: ConfigurableApplicationContext) {
            logger.info("Starting IT -> ${LocalDateTime.now()}")
            logger.info("Starting service 1 at ${dockerCompose.getServiceHost("postgres_1", 5432)}")
            logger.info("Starting service 1 at ${dockerCompose.getServiceHost("postgres-es_1", 5432)}")
            logger.info("End IT -> ${LocalDateTime.now()}")
            logger.info("Time Elapsed IT -> ${ChronoUnit.MILLIS.between(startup, LocalDateTime.now())} ms")
        }

        companion object {
            val logger: Logger = LoggerFactory.getLogger(VideoSeriesControllerITTest::class.java)
            private val startup: LocalDateTime = LocalDateTime.now()
        }
    }
    private class DockerCompose(files: List<File>) : DockerComposeContainer<DockerCompose>(files)

}
