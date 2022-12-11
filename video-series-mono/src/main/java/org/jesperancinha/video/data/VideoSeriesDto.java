package org.jesperancinha.video.data;

import lombok.Builder;

import java.math.BigDecimal;

public record VideoSeriesDto(
        Long id,
        String name,
        Integer volumes,
        BigDecimal cashValue,
        Genre genre
) {
    @Builder
    public VideoSeriesDto {}
}
