package org.jesperancinha.video.query.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.jesperancinha.video.core.data.VideoSeriesDto;
import org.jesperancinha.video.core.events.AddSeriesEvent;
import org.jesperancinha.video.query.queries.FindAllVideoSeriesQuery;
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
    @SuppressWarnings("unused")
    public void on(AddSeriesEvent event) {
        videoSeriesRepository.save(VideoSeries
                .builder()
                .name(event.getName())
                .volumes(event.getVolumes())
                .genre(event.getGenre())
                .cashValue(event.getCashValue())
                .build());
    }

    @QueryHandler
    @SuppressWarnings("unused")
    public List<VideoSeriesDto> handle(FindAllVideoSeriesQuery query) {
        return videoSeriesRepository.findAll().stream().map(
                videoSeries -> VideoSeriesDto.builder()
                        .name(videoSeries.getName())
                        .volumes(videoSeries.getVolumes())
                        .cashValue(videoSeries.getCashValue())
                        .genre(videoSeries.getGenre())
                        .build()).collect(Collectors.toList());
    }

}
