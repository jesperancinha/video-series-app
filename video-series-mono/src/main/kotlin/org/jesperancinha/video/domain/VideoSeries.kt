package org.jesperancinha.video.domain

import org.jesperancinha.video.data.Genre
import java.math.BigDecimal
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class VideoSeries (
    @Id
    val id: Long? = null,
    val name: String? = null,
    val volumes: Int? = null,
    val cashValue: BigDecimal? = null,
    val genre: Genre? = null
)