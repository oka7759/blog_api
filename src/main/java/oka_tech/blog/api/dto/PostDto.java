package oka_tech.blog.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import oka_tech.blog.api.entity.Post;

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

    public PostDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        views = post.getViews();
        imageUrl = post.getImageUrl();
        description = post.getDescription();
        createdAt = post.getCreatedAt();
        post.getTags().forEach(tag -> tags.add(new TagDto(tag.getId(), tag.getName())));
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
