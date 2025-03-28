package oka_tech.blog.api.dto;

import lombok.Data;
import oka_tech.blog.api.entity.Post;
import oka_tech.blog.api.entity.Series;

import java.util.List;

@Data
public class SearchResp {
    List<PostDto> posts;
    List<SeriesDto> series;

    public SearchResp( List<SeriesDto> series,List<PostDto> posts) {
        this.posts = posts;
        this.series = series;
    }
}
