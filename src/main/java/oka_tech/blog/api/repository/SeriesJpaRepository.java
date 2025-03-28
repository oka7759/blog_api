package oka_tech.blog.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import oka_tech.blog.api.dto.QSeriesDto;
import oka_tech.blog.api.dto.SeriesDto;
import oka_tech.blog.api.entity.Series;
import org.springframework.stereotype.Repository;

import java.util.List;

import static oka_tech.blog.api.entity.QPost.post;
import static oka_tech.blog.api.entity.QSeries.series;


@Repository
@RequiredArgsConstructor
public class SeriesJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public List<SeriesDto> findBySeriesByTitle(String title) {
        return queryFactory
                .select(new QSeriesDto(series))
                .from(series)
                .where(series.title.eq(title))
                .fetch();
    }


}
