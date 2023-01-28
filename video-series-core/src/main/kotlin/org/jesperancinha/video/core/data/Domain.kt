package org.jesperancinha.video.core.data

import java.math.BigDecimal

enum class Genre {
    SITCOM, DRAMA, HORROR
}

data class VideoSeriesDto(
    val id: String? = null,
    val name: String,
    val volumes: Int,
    val cashValue: BigDecimal,
    val genre: Genre,
)