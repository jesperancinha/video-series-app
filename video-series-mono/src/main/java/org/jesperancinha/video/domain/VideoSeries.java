package org.jesperancinha.video.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jesperancinha.video.data.Genre;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VideoSeries {
    @Id
    Long id;
    String name;
    Integer volumes;
    BigDecimal cashValue;
    Genre genre;
}
