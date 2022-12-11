package org.jesperancinha.video;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.servers.Server;

import org.axonframework.config.DefaultConfigurer;
import org.jesperancinha.video.command.aggregates.VideoSeriesAggregate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

//@OpenAPIDefinition(
//        info = @Info(title = "OpenAPI definition"),
//        servers = @Server(url = "${vsa.server.url}",
//                description = "Server URL")
//)
@SpringBootApplication(exclude = {
        ErrorMvcAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
public class VideoAppCommandLauncher {
    public static void main(String[] args) {
        SpringApplication.run(VideoAppCommandLauncher.class);
    }
}
