package org.jesperancinha.video.command.aggregates;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand;
import org.jesperancinha.video.core.data.Genre;
import org.jesperancinha.video.core.events.AddSeriesEvent;

import java.math.BigDecimal;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Data
@Aggregate
@NoArgsConstructor
public class VideoSeriesAggregate {

    @AggregateIdentifier
    private String id;

    private String name;

    private Integer volumes;

    private BigDecimal cashValue;

    private Genre genre;

    @CommandHandler
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
        this.name = event.getName();
        this.volumes = event.getVolumes();
        this.cashValue = event.getCashValue();
        this.genre = event.getGenre();
    }

}
