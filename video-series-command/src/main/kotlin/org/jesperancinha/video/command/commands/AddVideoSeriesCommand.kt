package org.jesperancinha.video.command.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.jesperancinha.video.core.data.Genre
import java.math.BigDecimal

data class AddVideoSeriesCommand(
    @TargetAggregateIdentifier
    val id: String? = null,
    val name: String? = null,
    val volumes: Int? = null,
    val cashValue: BigDecimal? = null,
    val genre: Genre? = null,
)