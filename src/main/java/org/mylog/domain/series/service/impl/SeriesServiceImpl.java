package org.mylog.domain.series.service.impl;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.blog.service.BlogService;
import org.mylog.domain.series.domain.Series;
import org.mylog.domain.series.dto.SeriesRegisterDto;
import org.mylog.domain.series.repository.SeriesRepository;
import org.mylog.domain.series.service.SeriesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesServiceImpl implements SeriesService {
    private final SeriesRepository seriesRepository;
    private final BlogService blogService;

    @Override
    public List<Series> findAllByBlogId(Long id) {
        return seriesRepository.findByBlogId(id);
    }

    @Override
    public Series registerSeries(SeriesRegisterDto seriesRegisterDto) {
        Blog blog = blogService.getBlogFindById(seriesRegisterDto.getBlogId()).orElseThrow();

        Series series = Series.builder()
                .name(seriesRegisterDto.getName())
                .blog(blog)
                .build();

        return seriesRepository.save(series);
    }
}
