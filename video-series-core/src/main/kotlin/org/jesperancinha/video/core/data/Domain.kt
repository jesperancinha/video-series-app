package org.jesperancinha.video.core.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

enum class Genre {
    SITCOM, DRAMA, HORROR
}

data class VideoSeriesDto(
    @JsonProperty
    val id: String? = null,
    @JsonProperty
    val name: String,
    @JsonProperty
    val volumes: Int,
    @JsonProperty
    val cashValue: BigDecimal,
    @JsonProperty
    val genre: Genre,
)