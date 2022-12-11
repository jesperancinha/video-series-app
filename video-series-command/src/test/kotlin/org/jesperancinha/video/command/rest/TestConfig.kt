package org.jesperancinha.video.command.rest

import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
class TestConfig {
    @Bean
    fun commandGateway(): CommandGateway =
        DefaultCommandGateway.builder().commandBus(SimpleCommandBus.builder().build()).build()
}