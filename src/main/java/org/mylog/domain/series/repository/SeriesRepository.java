package org.mylog.domain.series.repository;

import org.mylog.domain.series.domain.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findAllByBlogId(Long blogId);
}
