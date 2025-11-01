package org.jesperancinha.video

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.jesperancinha.video.data.Genre.SITCOM
import org.jesperancinha.video.data.VideoSeriesDto
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus.OK
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
            name = "3rd Rock from the Sun",
            cashValue = BigDecimal.valueOf(1000000),
            genre = SITCOM
        )
        val responseEntity = restTemplate.postForEntity("/video/series", film, VideoSeriesDto::class.java)
        responseEntity.statusCode shouldBe OK
        val videoHistoryEntity = restTemplate.getForEntity("/video/history", Array<VideoSeriesDto>::class.java)
        videoHistoryEntity.statusCode shouldBe OK
        val videoHistory = videoHistoryEntity.body!!
        videoHistory.shouldHaveSize(1)
        videoHistory[0] shouldBe film
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