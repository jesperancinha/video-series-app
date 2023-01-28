package org.jesperancinha.video.query.rest

import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.jesperancinha.video.core.data.VideoSeriesDto
import org.jesperancinha.video.query.queries.FindAllVideoSeriesQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/video-series")
class VideoSeriesController(private val queryGateway: QueryGateway) {
    @GetMapping
    fun gertAllVideoSeries(): List<VideoSeriesDto> {
        return queryGateway.query(
            FindAllVideoSeriesQuery(), ResponseTypes.multipleInstancesOf(
                VideoSeriesDto::class.java
            )
        ).join()
    }
}