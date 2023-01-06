package org.jesperancinha.video.data

import java.math.BigDecimal

data class VideoSeriesDto (
    val id: Long,
    val name: String,
    val volumes: Int = 1,
    val cashValue: BigDecimal,
    val genre: Genre
)