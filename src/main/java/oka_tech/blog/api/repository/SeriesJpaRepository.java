package oka_tech.blog.api.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import oka_tech.blog.api.dto.*;
import oka_tech.blog.api.entity.QSeries;
import oka_tech.blog.api.entity.QTag;
import oka_tech.blog.api.entity.Series;
import org.springframework.stereotype.Repository;

import java.util.List;

import static oka_tech.blog.api.entity.QPost.post;
import static oka_tech.blog.api.entity.QSeries.series;
import static oka_tech.blog.api.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class SeriesJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public Series findSeries(Long id) {
        return queryFactory.selectFrom(series).leftJoin(series.posts, post).where(series.id.eq(id)).fetchOne();
    }

    public SeriesDto findSeriesById(Long id) {
        queryFactory
                .from(series)
                .leftJoin(series.posts, post)
                .leftJoin(post.tags, tag)
                .where(series.id.eq(id))
                .transform(
                        GroupBy.groupBy(series.id)
                                .list(new QSeriesDto(
                                        series.id,
                                        series.title,
                                        series.description,
                                        series.createdAt,
                                        series.posts.size(),
                                        GroupBy.list(new PostDto(tag.id, tag.name))
                                );)
                );
        return results.isEmpty() ? null : filterNullTags(results.get(0));
    }
}
