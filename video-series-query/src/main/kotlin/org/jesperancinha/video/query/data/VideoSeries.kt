package org.jesperancinha.video.query.data

import jakarta.persistence.*
import org.jesperancinha.video.core.data.Genre
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "VIDEO_SERIES")
data class VideoSeries(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private val id: Long? = null,

    @Column val name: String,

    @Column val volumes: Int,

    @Column val cashValue: BigDecimal,

    @Column
    @Enumerated(EnumType.STRING) val genre: Genre,
)