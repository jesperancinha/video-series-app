package org.jesperancinha.video.query.rest;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.jesperancinha.video.core.data.VideoSeriesDto;
import org.jesperancinha.video.query.commands.FindAllVideoSeriesCommand;
import org.jesperancinha.video.query.data.VideoSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return queryGateway.query(new FindAllVideoSeriesCommand(), ResponseTypes.multipleInstancesOf(VideoSeriesDto.class))
                .join();
    }
}
