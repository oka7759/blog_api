package oka_tech.blog.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import oka_tech.blog.api.entity.Tag;
import org.springframework.core.metrics.StartupStep;

import java.time.LocalDateTime;

@Data
public class TagsDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private int postCount;

    @QueryProjection
    public TagsDto(Long id, String name, LocalDateTime createdAt, int postCount) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.postCount = postCount;
    }

    public TagsDto(Tag tag) {
        id = tag.getId();
        name = tag.getName();
        createdAt = tag.getCreatedAt();

    }


}
