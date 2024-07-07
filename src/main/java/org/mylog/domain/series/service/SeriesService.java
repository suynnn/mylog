package org.mylog.domain.series.service;

import org.mylog.domain.series.domain.Series;
import org.mylog.domain.series.dto.SeriesRegisterDto;

import java.util.List;
import java.util.Optional;

public interface SeriesService {
    List<Series> findAllByBlogId(Long blogId);

    Series registerSeries(SeriesRegisterDto seriesRegisterDto);
}
