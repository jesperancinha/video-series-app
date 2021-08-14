package org.jesperancinha.video.command.rest

import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.spring.SpringListener
import org.axonframework.extensions.mongo.MongoTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
class VideoSeriesControllerETETest(
    @Autowired
    private val mongoTemplate: MongoTemplate,
) : WordSpec() {

    companion object {

        @Container
        @JvmField
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5")

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            mongoDBContainer.start()
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
            registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort)
            registry.add("spring.data.mongodb.host", mongoDBContainer::getHost)
        }

    }

    override fun listeners(): List<TestListener> = listOf(SpringListener)

    init {
        "should receive data and responde correctly" should {
            "should send a video title" {

            }
        }

    }

    override fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)
        mongoDBContainer.isRunning.shouldBeTrue()

    }

}
