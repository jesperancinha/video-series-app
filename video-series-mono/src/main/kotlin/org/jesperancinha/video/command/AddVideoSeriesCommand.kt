package org.jesperancinha.video.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.jesperancinha.video.data.Genre
import java.math.BigDecimal

data class AddVideoSeriesCommand(
    @TargetAggregateIdentifier
    var id: String? = null,
    var name: String? = null,
    var volumes: Int? = null,
    var cashValue: BigDecimal? = null,
    var genre: Genre? = null
)