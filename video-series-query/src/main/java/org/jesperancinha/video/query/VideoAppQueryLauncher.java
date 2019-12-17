package org.jesperancinha.video.query;

import org.jesperancinha.video.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AxonConfig.class)
public class VideoAppQueryLauncher {
    public static void main(String[] args) {
            SpringApplication.run(VideoAppQueryLauncher.class);
        }
}
