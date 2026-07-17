package org.jesperancinha.video.event

import org.jesperancinha.video.data.Genre
import java.math.BigDecimal

data class VideoSeriesEvent(
    val id: Long? = null,
    val name: String? = null,
    val volumes: Int? = null,
    val cashValue: BigDecimal? = null,
    val genre: Genre? = null
)