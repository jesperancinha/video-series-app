package org.jesperancinha.video.query.jpa;

import org.jesperancinha.video.query.data.VideoSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSeriesRepository extends JpaRepository<VideoSeries, Long> {
}
