package org.jesperancinha.video.command.rest

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
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
//
//                val film = VideoSeriesDto.builder()
//                    .name("Nightmare on Elm Street I")
//                    .cashValue(BigDecimal.valueOf(1_000_000))
//                    .genre(Genre.HORROR)
//                    .build()
//
//                val responseEntity = testRestTemplate.restTemplate.postForEntity<Any>("/video-series", film)
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

}
