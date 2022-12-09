package org.jesperancinha.video.command.config;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfiguration {

    @Bean
    public CommandGateway commandGateway() {
        return DefaultCommandGateway.builder()
                .commandBus(AsynchronousCommandBus
                        .builder()
                        .build())
                .build();
    }

}
