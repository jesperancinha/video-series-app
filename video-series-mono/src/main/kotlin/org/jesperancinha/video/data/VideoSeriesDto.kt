package org.jesperancinha.video.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class VideoSeriesDto (
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("volumes")
    val volumes: Int = 1,
    @JsonProperty("cashValue")
    val cashValue: BigDecimal,
    @JsonProperty("genre")
    val genre: Genre
)