package org.mylog.domain.series.dto;

import lombok.Getter;
import lombok.Setter;
import org.mylog.domain.series.domain.Series;

@Getter
@Setter
public class SeriesDto {
    private Long id;
    private String name;

    public SeriesDto(Series series) {
        this.id = series.getId();
        this.name = series.getName();
    }
}
