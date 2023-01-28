package org.jesperancinha.video.core.events

import org.jesperancinha.video.core.data.Genre
import java.math.BigDecimal

/**
 * Unfortunately, serialization of the events is not possible using records and lombok as of now.
 */
class AddSeriesEvent(
    val id: String,
    val name: String,
    val volumes: Int,
    val cashValue: BigDecimal,
    val genre: Genre
)