package org.jesperancinha.video.domain

import org.jesperancinha.video.data.Genre
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class VideoSeries (
    @Id
    var id: Long? = null,
    var name: String? = null,
    var volumes: Int? = null,
    var cashValue: BigDecimal? = null,
    var genre: Genre? = null
)