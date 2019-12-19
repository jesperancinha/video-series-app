package org.jesperancinha.video.query.rest;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jesperancinha.video.core.data.VideoSeriesDto;
import org.jesperancinha.video.query.queries.FindAllVideoSeriesQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/video-series")
public class VideoSeriesController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<VideoSeriesDto> gertAllVideoSeries() {
        return queryGateway.query(new FindAllVideoSeriesQuery(), ResponseTypes.multipleInstancesOf(VideoSeriesDto.class))
                .join();
    }
}
