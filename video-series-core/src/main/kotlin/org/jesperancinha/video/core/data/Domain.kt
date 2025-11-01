package org.jesperancinha.video.core.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

enum class Genre {
    SITCOM, DRAMA, ACTION
}

data class VideoSeriesDto(
    @param:JsonProperty("id")
    val id: String? = null,
    @param:JsonProperty("name")
    val name: String,
    @param:JsonProperty("volumes")
    val volumes: Int = 1,
    @param:JsonProperty("cashValue")
    val cashValue: BigDecimal,
    @param:JsonProperty("genre")
    val genre: Genre,
)