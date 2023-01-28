package org.jesperancinha.video

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class, DataSourceAutoConfiguration::class])
@OpenAPIDefinition(
    info = Info(title = "OpenAPI definition"),
    servers = [Server(url = "\${vsa.server.url}", description = "Server URL")]
)
class VideoAppCommandLauncher

fun main(args: Array<String>) {
    SpringApplication.run(VideoAppCommandLauncher::class.java, *args).start()
}