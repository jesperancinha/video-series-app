package org.jesperancinha.video.command.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.Configurer;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.MessageHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.Repository;
import org.axonframework.springboot.autoconfig.XStreamAutoConfiguration;
import org.jesperancinha.video.command.aggregates.VideoSeriesAggregate;
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand;
import org.jesperancinha.video.command.commands.VsaCommandGateway;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.function.BiFunction;

@Configuration
@Slf4j
public class CommandConfiguration {

    @Bean
    public VsaCommandGateway commandGateway() throws Exception {
        val commandBus = SimpleCommandBus
                .builder()
                .build();
        commandBus.subscribe(AddVideoSeriesCommand.class.getName(), new MessageHandler<CommandMessage<?>>() {
            @Override
            public Object handle(CommandMessage<?> message) throws Exception {
                return AddVideoSeriesCommand.builder().build();
            }
        });
        MessageDispatchInterceptor messageDispatchInterceptor = messages -> (index, command) -> {
            log.info("Dispatching a command {}.", command);
            return command;
        };
        commandBus.registerDispatchInterceptor(messageDispatchInterceptor);
        return CommandGatewayFactory.builder()
                .commandBus(commandBus)
                .dispatchInterceptors(messageDispatchInterceptor)
                .build().createGateway(VsaCommandGateway.class);
    }
}
