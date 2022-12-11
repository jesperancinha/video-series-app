package org.jesperancinha.video.repository;

import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.video.domain.VideoSeries;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class VsaRepository {
    private static final HashMap<String, VideoSeries> videoSeriesStore = new HashMap<>();

    public void saveVideoSeriesHistory(VideoSeries videoSeries) {
        videoSeriesStore.put(videoSeries.getId().toString(), videoSeries);
        log.info("Video series added to hashmap! {}", videoSeries);
        log.info("Recorded history is:");
        videoSeriesStore.forEach((id, recordedVideoSeries) -> log.info("{} - {}", id, recordedVideoSeries));
    }

    public List<VideoSeries> readAll() {
        return videoSeriesStore.values().stream().toList();
    }
}
