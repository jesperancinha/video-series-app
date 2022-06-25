package org.jesperancinha.video.core.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jesperancinha.video.core.data.Genre;

import java.math.BigDecimal;

/**
 * Unfortunately, serialization of the events is not possible using records and lombok as of now.
 */
@Builder
@Getter
@Setter
public class AddSeriesEvent {
    String id;
    String name;
    Integer volumes;
    BigDecimal cashValue;
    Genre genre;
}