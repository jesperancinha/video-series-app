package org.jesperancinha.video.core.data;

import lombok.Builder;

import java.math.BigDecimal;

public record VideoSeriesDto(
        String name,
        Integer volumes,
        BigDecimal cashValue,
        Genre genre
) {
    @Builder
    public VideoSeriesDto {}
}
