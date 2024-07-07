package org.mylog.domain.series.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeriesRegisterDto {
    private String name;
    private Long blogId;
}
