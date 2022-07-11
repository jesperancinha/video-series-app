package org.jesperancinha.video.query;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.jesperancinha.video.core.configuration.AxonConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@SpringBootApplication
@Import(AxonConfig.class)
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
