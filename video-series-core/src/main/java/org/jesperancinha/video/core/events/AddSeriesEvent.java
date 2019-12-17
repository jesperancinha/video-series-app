package org.jesperancinha.video.core.events;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AddSeriesEvent {

    private String id;

    private String name;

    private Integer volumes;

    private BigDecimal cashValue;

    private String genre;

}
