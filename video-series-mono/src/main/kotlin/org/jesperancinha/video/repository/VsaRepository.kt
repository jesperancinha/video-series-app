package org.jesperancinha.video.repository

import org.jesperancinha.video.domain.VideoSeries
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class VsaRepository {
    fun saveVideoSeriesHistory(videoSeries: VideoSeries) {
        videoSeriesStore[videoSeries.id.toString()] = videoSeries
        logger.info("Video series added to hashmap! {}", videoSeries)
        logger.info("Recorded history is:")
        videoSeriesStore.forEach { (id: String?, recordedVideoSeries: VideoSeries?) ->
            logger.info(
                "{} - {}",
                id,
                recordedVideoSeries
            )
        }
    }

    fun readAll(): List<VideoSeries> {
        return videoSeriesStore.values.stream().toList()
    }

    companion object {
        private val videoSeriesStore = HashMap<String, VideoSeries>()
        val logger: Logger = LoggerFactory.getLogger(VsaRepository::class.java)
    }
}