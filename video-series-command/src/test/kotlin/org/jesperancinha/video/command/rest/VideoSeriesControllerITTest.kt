package org.jesperancinha.video.command.rest

//import org.springframework.data.mongodb.core.query.Query
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.WordSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.bson.Document
import org.jesperancinha.video.command.aggregates.VideoSeriesAggregate
import org.jesperancinha.video.core.data.Genre
import org.jesperancinha.video.core.data.Genre.HORROR
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.postForEntity
import java.math.BigDecimal

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = [VideoSeriesCommandInitializer::class])
class VideoSeriesControllerITTest(
    @Autowired
    private val testRestTemplate: TestRestTemplate,
    @Autowired
    private val mongoClient: MongoClient,
) : WordSpec(
    {
        "should receive data and respond correctly" should {
            "should send a video title" {

                mongoClient
                    .getDatabase("axonframework")
                    .getCollection("domainevents")
                    .find().toList()
                    .shouldBeEmpty()

                val film = VideoSeriesDto(
                    name = "Nightmare on Elm Street I",
                    cashValue = BigDecimal.valueOf(1_000_000),
                    volumes = 1,
                    genre = HORROR
                )

                val responseEntity = testRestTemplate.restTemplate.postForEntity<VideoSeriesDto>("/video-series", film)

                responseEntity.statusCode shouldBe OK

                val resultingDocumentList = mongoClient
                    .getDatabase("axonframework")
                    .getCollection("domainevents")
                    .find()
                resultingDocumentList.toList()
                    .shouldHaveSize(1)
                val filmOnEventQueue: VideoSeriesAggregate = resultingDocumentList.findFirstDocumentInCollection()
                filmOnEventQueue.id.shouldNotBeNull()
                filmOnEventQueue.name shouldBe "Nightmare on Elm Street I"
                filmOnEventQueue.genre shouldBe HORROR
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