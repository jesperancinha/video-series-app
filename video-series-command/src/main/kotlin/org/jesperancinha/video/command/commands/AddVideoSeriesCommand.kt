package org.jesperancinha.video.command.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.jesperancinha.video.core.data.Genre
import java.math.BigDecimal

class AddVideoSeriesCommand(
    @TargetAggregateIdentifier
    var id: String? = null,
    var name: String? = null,
    var volumes: Int? = null,
    var cashvalue: BigDecimal? = null,
    var genre: Genre? = null,
)