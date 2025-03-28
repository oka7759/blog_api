package oka_tech.blog.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import oka_tech.blog.api.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SimplePostDto {
    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private List<Tag> tags= new ArrayList<>();

    @QueryProjection
    public SimplePostDto(Long postId, String title, LocalDateTime createdAt, List<Tag> tags) {
        this.postId = postId;
        this.title = title;
        this.createdAt = createdAt;
        this.tags = tags;
    }
}
