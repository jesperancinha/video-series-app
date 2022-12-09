package org.jesperancinha.video;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.servers.Server;

import org.axonframework.springboot.autoconfig.EventProcessingAutoConfiguration;
import org.axonframework.springboot.autoconfig.InfraConfiguration;
import org.jesperancinha.video.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@OpenAPIDefinition(
//        info = @Info(title = "OpenAPI definition"),
//        servers = @Server(url = "${vsa.server.url}",
//                description = "Server URL")
//)
public class VideoAppCommandLauncher {
    public static void main(String[] args) {
        SpringApplication.run(VideoAppCommandLauncher.class);
    }
}
