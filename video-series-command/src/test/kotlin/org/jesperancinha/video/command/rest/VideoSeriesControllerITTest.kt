package org.jesperancinha.video.command.rest

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.WordSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.bson.Document
import org.jesperancinha.video.command.aggregates.VideoSeriesAggregate
import org.jesperancinha.video.common.VideoSeriesInitializer
import org.jesperancinha.video.core.data.Genre.SITCOM
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import org.springframework.web.client.postForEntity
import java.math.BigDecimal

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = [VideoSeriesInitializer::class])
@Disabled
class VideoSeriesControllerITTest(
    @LocalServerPort private val port: Int,
    @Autowired
    private val mongoClient: MongoClient,
) : WordSpec(

    {
        val restTestClient =
            RestTestClient.bindToServer().baseUrl("http://localhost:$port").build();

        "should receive data and respond correctly" should {
            "should send a video title" {

                mongoClient
                    .getDatabase("axonframework")
                    .getCollection("domainevents")
                    .find().toList()
                    .shouldBeEmpty()

                val film = VideoSeriesDto(
                    name = "3rd Rock from the Sun",
                    cashValue = BigDecimal.valueOf(1_000_000),
                    volumes = 1,
                    genre = SITCOM
                )

                val responseEntity = restTestClient.post()
                    .uri("/video-series")
                    .body(film)
                    .exchange()
                    .expectBody<VideoSeriesDto>()
                    .returnResult()

                responseEntity.status shouldBe OK

                val resultingDocumentList = mongoClient
                    .getDatabase("axonframework")
                    .getCollection("domainevents")
                    .find()
                resultingDocumentList.toList()
                    .shouldHaveSize(1)
                val filmOnEventQueue: VideoSeriesAggregate = resultingDocumentList.findFirstDocumentInCollection()
                filmOnEventQueue.id.shouldNotBeNull()
                filmOnEventQueue.name shouldBe "3rd Rock from the Sun"
                filmOnEventQueue.genre shouldBe SITCOM
                filmOnEventQueue.cashValue shouldBe BigDecimal.valueOf(1000000)
                filmOnEventQueue.volumes shouldBe 1
            }
        }
    }
) {
    override fun extensions(): List<Extension> = listOf(SpringExtension)
}

private fun <TResult : Document> FindIterable<TResult>.findFirstDocumentInCollection(): VideoSeriesAggregate =
    XmlMapper().readValue(first()["serializedPayload"].toString(), VideoSeriesAggregate::class.java)