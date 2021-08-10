package org.jesperancinha.video.core.events;

import lombok.Builder;
import org.jesperancinha.video.core.data.Genre;

import java.math.BigDecimal;

public record AddSeriesEvent(
        String id,
        String name,
        Integer volumes,
        BigDecimal cashValue,
        Genre genre
) {
    @Builder
    public AddSeriesEvent {
    }
}
