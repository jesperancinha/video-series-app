package org.jesperancinha.video.event;

import org.axonframework.eventhandling.EventHandler;
import org.jesperancinha.video.domain.VideoSeries;
import org.springframework.stereotype.Component;

import org.jesperancinha.video.repository.VsaRepository;

@Component
public class VideoSeriesQueryHandler {

    private final VsaRepository vsaRepository;

    public VideoSeriesQueryHandler(VsaRepository vsaRepository) {
        this.vsaRepository = vsaRepository;
    }

    @EventHandler
    public void on(VideoSeriesEvent event) {
        vsaRepository.saveVideoSeriesHistory(new VideoSeries(
                event.getId(),
                event.getName(),
                event.getVolumes(),
                event.getCashValue(),
                event.getGenre())
        );
    }
}
