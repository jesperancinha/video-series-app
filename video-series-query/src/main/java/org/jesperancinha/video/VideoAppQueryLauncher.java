package org.jesperancinha.video;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "OpenAPI definition"),
        servers = @Server(url = "${vsa.server.url}",
                description = "Server URL")
)
public class VideoAppQueryLauncher {
    public static void main(String[] args) {
        SpringApplication.run(VideoAppQueryLauncher.class);
    }
}
