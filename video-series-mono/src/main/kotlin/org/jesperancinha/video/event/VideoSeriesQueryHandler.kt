package org.jesperancinha.video.event

import org.axonframework.eventhandling.EventHandler
import org.jesperancinha.video.domain.VideoSeries
import org.jesperancinha.video.repository.VsaRepository
import org.springframework.stereotype.Component

@Component
class VideoSeriesQueryHandler(private val vsaRepository: VsaRepository) {
    @EventHandler
    fun on(event: VideoSeriesEvent) {
        vsaRepository.saveVideoSeriesHistory(
            VideoSeries(
                event.id,
                event.name,
                event.volumes,
                event.cashValue,
                event.genre
            )
        )
    }
}