package org.jesperancinha.video.event;

import lombok.Data;
import lombok.Value;
import org.jesperancinha.video.data.Genre;

import java.math.BigDecimal;

@Value
@Data
public class VideoSeriesEvent {
    Long id;
    String name;
    Integer volumes;
    BigDecimal cashValue;
    Genre genre;
}