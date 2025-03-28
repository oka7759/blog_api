package oka_tech.blog.api.dto;

import lombok.Getter;
import oka_tech.blog.api.entity.Series;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SeriesResp extends SeriesDto {
    private final List<PostDto> posts;

    public SeriesResp(Series series, List<PostDto> posts) {
        super(series);
        this.posts = posts;
    }
}
