package org.jesperancinha.video.command;

import org.jesperancinha.video.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AxonConfig.class)
public class VideoAppCommandLauncher {
    public static void main(String[] args) {
        SpringApplication.run(VideoAppCommandLauncher.class);
    }
}
