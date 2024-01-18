package org.jesperancinha.video

import org.assertj.core.api.Assertions
import org.jesperancinha.video.data.Genre
import org.jesperancinha.video.data.VideoSeriesDto
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [VsaMonoApplicationTests.VideoSeriesCommandInitializer::class])
internal class VsaMonoApplicationTests {
    @Autowired
    private val testRestTemplate: TestRestTemplate? = null
    @Test
    fun contextLoads() {
        val restTemplate = testRestTemplate!!.restTemplate
        val film: Any = VideoSeriesDto(
             id = 123L,
            name = "Nightmare on Elm Street I",
            cashValue = BigDecimal.valueOf(1000000),
            genre =Genre.HORROR)
        val responseEntity = restTemplate.postForEntity("/video/series", film, VideoSeriesDto::class.java)
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        val videoHistoryEntity = restTemplate.getForEntity("/video/history", Array<VideoSeriesDto>::class.java)
        Assertions.assertThat(videoHistoryEntity.statusCode).isEqualTo(HttpStatus.OK)
        val videoHistory = videoHistoryEntity.body!!
        Assertions.assertThat(videoHistory).hasSize(1)
        Assertions.assertThat(videoHistory[0]).isEqualTo(film)
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
                    .withLocalCompose(false)}

            init {
                dockerCompose.start()
            }
        }
    }
}