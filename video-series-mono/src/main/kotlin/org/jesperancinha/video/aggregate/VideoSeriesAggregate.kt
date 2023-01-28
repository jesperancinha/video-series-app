package org.jesperancinha.video.aggregate

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.jesperancinha.video.command.AddVideoSeriesCommand
import org.jesperancinha.video.data.Genre
import org.jesperancinha.video.event.VideoSeriesEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal

@Aggregate
class VideoSeriesAggregate {
    @AggregateIdentifier
    private var id: String? = null
    private var name: String? = null
    private var volumes: Int? = null
    private var cashValue: BigDecimal? = null
    private var genre: Genre? = null

    constructor()

    @CommandHandler
    constructor(addVideoSeriesCommand: AddVideoSeriesCommand) {
        logger.info("Created addVideoSeriesCommand {}", addVideoSeriesCommand)
        val event = VideoSeriesEvent(
            addVideoSeriesCommand.id?.toLong(),
            addVideoSeriesCommand.name,
            addVideoSeriesCommand.volumes,
            addVideoSeriesCommand.cashValue,
            addVideoSeriesCommand.genre
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: VideoSeriesEvent) {
        id = event.id.toString()
        name = event.name
        volumes = event.volumes
        cashValue = event.cashValue
        genre = event.genre
    }
    
    companion object {
        val logger: Logger = LoggerFactory.getLogger(VideoSeriesAggregate::class.java)
    }
}