package org.jesperancinha.video.command.aggregates

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.jesperancinha.video.command.commands.AddVideoSeriesCommand
import org.jesperancinha.video.core.data.Genre
import org.jesperancinha.video.core.events.AddSeriesEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.*

@Aggregate
class VideoSeriesAggregate {
    @AggregateIdentifier
    var id: String? = null
    var name: String? = null
    var volumes: Int? = null
    var cashValue: BigDecimal? = null
    var genre: Genre? = null

    constructor()

    @CommandHandler
    constructor(command: AddVideoSeriesCommand) {
        logger.info("Created addVideoSeriesCommand {}", command)
        val event =             AddSeriesEvent(
            id = UUID.randomUUID().toString(),
            cashValue = requireNotNull(command.cashValue),
            genre = requireNotNull(command.genre),
            name = requireNotNull(command.name),
            volumes = requireNotNull(command.volumes)
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: AddSeriesEvent) {
        id = event.id
        name = event.name
        volumes = event.volumes
        cashValue = event.cashValue
        genre = event.genre
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(VideoSeriesAggregate::class.java)
    }
}