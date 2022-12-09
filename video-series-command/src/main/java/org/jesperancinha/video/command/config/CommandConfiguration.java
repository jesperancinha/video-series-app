package org.jesperancinha.video.command.config;

import lombok.val;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.Configurer;
import org.axonframework.modelling.command.Repository;
import org.axonframework.springboot.autoconfig.XStreamAutoConfiguration;
import org.jesperancinha.video.command.aggregates.VideoSeriesAggregate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class CommandConfiguration {

    @Bean
    public CommandGateway commandGateway() {
        val commandBus = SimpleCommandBus
                .builder()
                .build();
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .build();
    }
}
