package oka_tech.blog.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import oka_tech.blog.api.entity.Series;

import java.time.LocalDateTime;

@Data
public class SeriesDto {

    Long id;
    String title;
    String description;
    LocalDateTime createdAt;
    int postCount;

    @QueryProjection
    public SeriesDto(Series series) {
        id=series.getId();
        title=series.getTitle();
        description=series.getDescription();
        createdAt=series.getCreatedAt();
        postCount = series.getPosts().size();
    }
}
