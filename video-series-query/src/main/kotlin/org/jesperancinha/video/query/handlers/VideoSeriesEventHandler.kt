package org.jesperancinha.video.query.handlers

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.jesperancinha.video.core.events.AddSeriesEvent
import org.jesperancinha.video.query.data.VideoSeries
import org.jesperancinha.video.query.jpa.VideoSeriesRepository
import org.jesperancinha.video.query.queries.FindAllVideoSeriesQuery
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@ProcessingGroup("video-series")
class VideoSeriesEventHandler(private val videoSeriesRepository: VideoSeriesRepository) {
    @EventHandler
    @Suppress("unused")
    fun on(event: AddSeriesEvent) {
        videoSeriesRepository.save(
            VideoSeries(
                name = event.name,
                volumes = event.volumes,
                genre = event.genre,
                cashValue = event.cashValue
            )
        )
    }

    @QueryHandler
    @Suppress("unused")
    fun handle(query: FindAllVideoSeriesQuery?): List<VideoSeriesDto> {
        return videoSeriesRepository.findAll().filterNotNull().map { videoSeries ->
            VideoSeriesDto(
                name = videoSeries.name,
                volumes = videoSeries.volumes,
                genre = videoSeries.genre,
                cashValue = videoSeries.cashValue
            )
        }
    }
}