package org.jesperancinha.video.query.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.jesperancinha.video.core.data.VideoSeriesDto;
import org.jesperancinha.video.core.events.AddSeriesEvent;
import org.jesperancinha.video.core.events.ConfirmSeriesEvent;
import org.jesperancinha.video.query.commands.FindAllVideoSeriesCommand;
import org.jesperancinha.video.query.data.VideoSeries;
import org.jesperancinha.video.query.jpa.VideoSeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ProcessingGroup("video-series")
public class VideoSeriesEventHandler {

    private final VideoSeriesRepository videoSeriesRepository;

    public VideoSeriesEventHandler(VideoSeriesRepository videoSeriesRepository) {
        this.videoSeriesRepository = videoSeriesRepository;
    }

    @EventHandler
    public void on(AddSeriesEvent event) {
        videoSeriesRepository.save(VideoSeries
                .builder()
                .name(event.getName())
                .volumes(event.getVolumes())
                .genre(event.getGenre())
                .cashValue(event.getCashValue())
                .build());
    }

    @EventHandler
    public void on(ConfirmSeriesEvent event) {
        System.out.println("Confirm");
    }


    @QueryHandler
    public List<VideoSeriesDto> handle(FindAllVideoSeriesCommand query) {
        return videoSeriesRepository.findAll().stream().map(
                videoSeries -> VideoSeriesDto.builder()
                        .name(videoSeries.getName())
                        .volumes(videoSeries.getVolumes())
                        .cashValue(videoSeries.getCashValue())
                        .genre(videoSeries.getGenre())
                        .build()).collect(Collectors.toList());
    }

}
