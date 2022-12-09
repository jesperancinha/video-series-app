package org.jesperancinha.video.command.aggregates;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand;
import org.jesperancinha.video.core.events.AddSeriesEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class VideoSeriesAggregate {

    @AggregateIdentifier
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public VideoSeriesAggregate() {
        System.out.println("Whatever");

    }

    @CommandHandler(payloadType = AddVideoSeriesCommand.class)
    public VideoSeriesAggregate(AddVideoSeriesCommand command) {
        apply(AddSeriesEvent.builder()
                .id(UUID.randomUUID().toString())
                .cashValue(command.getCashValue())
                .genre(command.getGenre())
                .name(command.getName())
                .volumes(command.getVolumes()).build()
        );
    }

    @EventSourcingHandler
    public void on(AddSeriesEvent event) {
        this.id = event.getId();
    }

}
