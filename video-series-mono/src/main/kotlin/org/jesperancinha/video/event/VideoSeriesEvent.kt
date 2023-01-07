package org.jesperancinha.video.event

import org.jesperancinha.video.data.Genre
import java.math.BigDecimal

data class VideoSeriesEvent(
    var id: Long? = null,
    var name: String? = null,
    var volumes: Int? = null,
    var cashValue: BigDecimal? = null,
    var genre: Genre? = null
)