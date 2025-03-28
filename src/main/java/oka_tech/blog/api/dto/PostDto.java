package oka_tech.blog.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private Long views;

    private String imageUrl;

    private String description;

    private LocalDateTime createdAt;

    private List<TagDto> tags = new ArrayList<>();

    @QueryProjection
    public PostDto(Long id, String title, String content, Long views, String imageUrl, String description, LocalDateTime createdAt, List<TagDto> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.imageUrl = imageUrl;
        this.description = description;
        this.createdAt = createdAt;
        this.tags = tags;
    }



    @Data
    public static class TagDto {

        private Long tagId;
        private String name;

        @QueryProjection
        public TagDto(Long tagId, String name) {
            this.tagId = tagId;
            this.name = name;
        }
    }
}
