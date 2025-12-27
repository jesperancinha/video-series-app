package org.jesperancinha.video

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.jesperancinha.video.data.Genre.SITCOM
import org.jesperancinha.video.data.VideoSeriesDto
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureRestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.TestPropertySourceUtils
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [VsaMonoApplicationTests.VideoSeriesCommandInitializer::class])
@AutoConfigureRestClient
internal class VsaMonoApplicationTests(
    @LocalServerPort private val port: Int,
){

    val restTestClient =
        RestTestClient.bindToServer().baseUrl("http://localhost:$port").build();

    @Test
    fun contextLoads() {
        val restTemplate = restTestClient
        val film: Any = VideoSeriesDto(
             id = 123L,
            name = "3rd Rock from the Sun",
            cashValue = BigDecimal.valueOf(1000000),
            genre = SITCOM
        )
        val responseEntity = restTemplate
            .post()
            .uri("/video/series")
            .body(film)
            .exchange()
            .expectBody<VideoSeriesDto>()
            .returnResult()
        responseEntity.status shouldBe OK
        val videoHistoryEntity = restTemplate
            .get()
            .uri("/video/history")
            .exchange()
            .expectBody<Array<VideoSeriesDto>>()
            .returnResult()
        videoHistoryEntity.status shouldBe OK
        val videoHistory = videoHistoryEntity.responseBody.shouldNotBeNull()
//        videoHistory.shouldHaveSize(1)
//        videoHistory[0] shouldBe film
    }

    class VideoSeriesCommandInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            logger.info("Starting IT -> {}", LocalDateTime.now())
            val mongoPort = dockerCompose.getServicePort("mongo_1", 27017)
            TestPropertySourceUtils
                .addInlinedPropertiesToEnvironment(
                    applicationContext, "video.series.mongo.port=$mongoPort"
                )
        }

        companion object {
            val logger = LoggerFactory.getLogger(VsaMonoApplicationTests::class.java)
            private val dockerCompose by lazy {
                DockerComposeContainer(listOf(File("docker-compose.yml")))
                    .withExposedService("mongo_1", 27017, Wait.forListeningPort())
            }
            init {
                dockerCompose.start()
            }
        }
    }
}