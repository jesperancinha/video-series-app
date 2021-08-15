package org.jesperancinha.video.command.rest

import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.spring.SpringListener
import org.jesperancinha.video.core.data.Genre
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.client.postForEntity
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
class VideoSeriesControllerITTest(
    @Autowired
    private val mongoTemplate: MongoTemplate,
    @Autowired
    private val testRestTemplate: TestRestTemplate,
) : WordSpec(
    {
        "should receive data and respond correctly" should {
            "should send a video title" {
                val allDomainEvents = mongoTemplate
                    .find(Query.query(Criteria()), Any::class.java, "domainevents")

                allDomainEvents shouldHaveSize 0

                val film = VideoSeriesDto.builder()
                    .name("Nightmare on Elm Street I")
                    .cashValue(BigDecimal.valueOf(1_000_000))
                    .genre(Genre.HORROR)
                    .build()

                val responseEntity = testRestTemplate.restTemplate.postForEntity<Any>("/video-series", film)

                responseEntity.statusCode shouldBe HttpStatus.OK
                val allPostDomainEvents = mongoTemplate
                    .find(Query.query(Criteria()), LinkedHashMap::class.java, "domainevents")

                allPostDomainEvents shouldHaveSize 1

                val filmOnEventQueue = allPostDomainEvents[0]["serializedPayload"].toString()

                filmOnEventQueue shouldContain "Nightmare on Elm Street I"
                filmOnEventQueue shouldContain "HORROR"
                filmOnEventQueue shouldContain "1000000"
            }
        }
    }
) {

    companion object {
        @Container
        @JvmField
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5")

        init {
            mongoDBContainer.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
            registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort)
            registry.add("spring.data.mongodb.host", mongoDBContainer::getHost)
            registry.add("spring.data.mongodb.database") { "axonframework" }
        }
    }

    override fun listeners(): List<TestListener> = listOf(SpringListener)

    override fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)
        mongoDBContainer.isRunning.shouldBeTrue()

    }

}
