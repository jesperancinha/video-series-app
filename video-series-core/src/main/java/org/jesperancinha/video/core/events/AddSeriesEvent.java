package org.jesperancinha.video.core.events;

import lombok.Builder;
import org.jesperancinha.video.core.data.Genre;

import java.math.BigDecimal;

/**
 * Unfortunately, serialization of the events is not possible using records and lombok as of now.
 */
@Builder
public class AddSeriesEvent {
    String id;
    String name;
    Integer volumes;
    BigDecimal cashValue;
    Genre genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVolumes() {
        return volumes;
    }

    public void setVolumes(Integer volumes) {
        this.volumes = volumes;
    }

    public BigDecimal getCashValue() {
        return cashValue;
    }

    public void setCashValue(BigDecimal cashValue) {
        this.cashValue = cashValue;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}