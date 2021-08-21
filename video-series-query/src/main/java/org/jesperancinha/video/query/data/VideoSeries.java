package org.jesperancinha.video.query.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jesperancinha.video.core.data.Genre;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "VIDEO_SERIES")
public class VideoSeries {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Integer volumes;

    @Column
    private BigDecimal cashValue;

    @Column
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VideoSeries that = (VideoSeries) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 950399058;
    }
}
