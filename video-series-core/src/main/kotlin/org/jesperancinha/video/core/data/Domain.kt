package org.jesperancinha.video.core.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

enum class Genre {
    SITCOM, DRAMA, HORROR
}

data class VideoSeriesDto(
    @JsonProperty("id")
    val id: String? = null,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("volumes")
    val volumes: Int = 1,
    @JsonProperty("cashValue")
    val cashValue: BigDecimal,
    @JsonProperty("genre")
    val genre: Genre,
)