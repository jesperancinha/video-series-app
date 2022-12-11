package org.jesperancinha.video.aggregate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.jesperancinha.video.command.AddVideoSeriesCommand;
import org.jesperancinha.video.data.Genre;
import org.jesperancinha.video.event.VideoSeriesEvent;

import java.math.BigDecimal;

@Aggregate
@Slf4j
@Getter
public class VideoSeriesAggregate {
    @AggregateIdentifier
    private String id;

    private String name;

    private Integer volumes;

    private BigDecimal cashValue;

    private Genre genre;

    public VideoSeriesAggregate() {
    }

    @CommandHandler
    public VideoSeriesAggregate(AddVideoSeriesCommand addVideoSeriesCommand) {
        log.info("Created addVideoSeriesCommand {}", addVideoSeriesCommand);
        VideoSeriesEvent event = new VideoSeriesEvent(
                Long.parseLong(addVideoSeriesCommand.getId()),
                addVideoSeriesCommand.getName(),
                addVideoSeriesCommand.getVolumes(),
                addVideoSeriesCommand.getCashValue(),
                addVideoSeriesCommand.getGenre());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VideoSeriesEvent event) {
        this.id = event.getId().toString();
        this.name = event.getName();
        this.volumes = event.getVolumes();
        this.cashValue = event.getCashValue();
        this.genre = event.getGenre();
    }
}
