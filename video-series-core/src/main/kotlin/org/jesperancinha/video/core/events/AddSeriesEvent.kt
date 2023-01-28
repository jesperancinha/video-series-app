package org.jesperancinha.video.core.events

import org.jesperancinha.video.core.data.Genre
import java.math.BigDecimal

class AddSeriesEvent(
    val id: String,
    val name: String,
    val volumes: Int,
    val cashValue: BigDecimal,
    val genre: Genre
)