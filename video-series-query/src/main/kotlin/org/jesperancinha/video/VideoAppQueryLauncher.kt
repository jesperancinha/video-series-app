package org.jesperancinha.video

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(title = "OpenAPI definition"),
    servers = [Server(url = "\${vsa.server.url}", description = "Server URL")]
)
class VideoAppQueryLauncher {

}
fun main(args: Array<String>) {
    SpringApplication.run(VideoAppQueryLauncher::class.java, *args).start()
}