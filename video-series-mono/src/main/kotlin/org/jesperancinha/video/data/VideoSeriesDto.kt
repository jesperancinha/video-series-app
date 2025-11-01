package org.jesperancinha.video.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class VideoSeriesDto (
    @param:JsonProperty("id")
    val id: Long,
    @param:JsonProperty("name")
    val name: String,
    @param:JsonProperty("volumes")
    val volumes: Int = 1,
    @param:JsonProperty("cashValue")
    val cashValue: BigDecimal,
    @param:JsonProperty("genre")
    val genre: Genre
)