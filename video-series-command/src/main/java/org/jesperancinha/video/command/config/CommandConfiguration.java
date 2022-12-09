package org.jesperancinha.video.command.config;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.jesperancinha.video.command.aggregates.VideoSeriesAggregate;
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfiguration {
//
//    @Bean
//    public CommandGateway commandGateway() {
//        Configurer configurer = DefaultConfigurer.defaultConfiguration();
//        configurer.registerCommandHandler(c -> new VideoSeriesAggregate(c.getComponent(AddVideoSeriesCommand.class)));
//        return DefaultCommandGateway.builder()
//                .commandBus(AsynchronousCommandBus
//                        .builder()
//                        .build())
//                .build();
//    }

}
