package org.jesperancinha.video.query.rest

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import org.jesperancinha.video.query.data.VideoSeries
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("postgres")
class VideoSeriesControllerTest(
    private val testRestTemplate: TestRestTemplate,
) : WordSpec({

    class VideoSeriesList : MutableList<VideoSeries> by ArrayList()

    "should receive data and respond correctly" should {
        "return empty list when no data available" {

            val responseEntity = testRestTemplate.getForEntity<VideoSeriesList>("/video-series", VideoSeriesList::class)

            responseEntity.shouldNotBeNull()
            (responseEntity.body as List<*>).shouldBeEmpty()
        }
    }

}) {
    companion object {

        @Container
        @JvmField
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:5")

        @Container
        @JvmStatic
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>().apply {
            withDatabaseName("vsa")
            withUsername("postgres")
            withPassword("admin")
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
        }

    }
}
