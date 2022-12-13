package org.jesperancinha.video;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jesperancinha.video.data.Genre;
import org.jesperancinha.video.data.VideoSeriesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jesperancinha.video.data.Genre.HORROR;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.testcontainers.containers.wait.strategy.Wait.forListeningPort;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
@ContextConfiguration(initializers = VsaMonoApplicationTests.VideoSeriesCommandInitializer.class)
class VsaMonoApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
        val film = VideoSeriesDto.builder()
                .id(123L)
                .name("Nightmare on Elm Street I")
                .cashValue(valueOf(1_000_000))
                .genre(HORROR)
                .build();
        val responseEntity = restTemplate.postForEntity("/video/series", film, VideoSeriesDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);

        ResponseEntity<VideoSeriesDto[]> videoHistoryEntity = restTemplate.getForEntity("/video/history", VideoSeriesDto[].class);
        assertThat(videoHistoryEntity.getStatusCode()).isEqualTo(OK);
        VideoSeriesDto[] videoHistory = videoHistoryEntity.getBody();
        assertThat(videoHistory).hasSize(1);
        assertThat(videoHistory[0]).isEqualTo(film);
    }

    @Slf4j
    public static class VideoSeriesCommandInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        private static LocalDateTime startup = LocalDateTime.now();
        private static DockerComposeContainer dockerCompose =
                new DockerComposeContainer(List.of(new File("docker-compose.yml")))
                        .withExposedService("mongo_1", 27017, forListeningPort())
                        .withLocalCompose(true);

        static {
            dockerCompose.start();
        }

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            log.info("Starting IT -> {}", LocalDateTime.now());
            val mongoPort = dockerCompose.getServicePort("mongo_1", 27017);
            TestPropertySourceUtils
                    .addInlinedPropertiesToEnvironment(
                            configurableApplicationContext,
                            String.format("video.series.mongo.port=%d", mongoPort)
                    );
        }
    }
}
