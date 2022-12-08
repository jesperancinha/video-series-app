package org.jesperancinha.video.query;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.servers.Server;

import org.axonframework.spring.config.SpringAxonConfiguration;
import org.axonframework.springboot.autoconfig.EventProcessingAutoConfiguration;
import org.axonframework.springboot.autoconfig.InfraConfiguration;
import org.axonframework.springboot.autoconfig.XStreamAutoConfiguration;
import org.jesperancinha.video.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class, XStreamAutoConfiguration.class, EventProcessingAutoConfiguration.class, SpringAxonConfiguration.class, InfraConfiguration.class})
//@OpenAPIDefinition(
//        info = @Info(title = "OpenAPI definition"),
//        servers = @Server(url = "${vsa.server.url}",
//                description = "Server URL")
//)
public class VideoAppQueryLauncher {

    public static void main(String[] args) {
        SpringApplication.run(VideoAppQueryLauncher.class);
    }

}
