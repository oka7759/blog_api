package oka_tech.blog.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import oka_tech.blog.api.dto.QTagsDto;
import oka_tech.blog.api.dto.TagsDto;
import oka_tech.blog.api.entity.QPost;
import org.springframework.stereotype.Repository;

import java.util.List;

import static oka_tech.blog.api.entity.QPost.post;
import static oka_tech.blog.api.entity.QTag.*;

@Repository
@RequiredArgsConstructor
public class TagJpaRepository {
    private final JPAQueryFactory queryFactory;

    public List<TagsDto> getTags(){
        return queryFactory
                .select(new QTagsDto(tag.id,tag.name, tag.createdAt, tag.posts.size()))
                .from(tag)
                .leftJoin(tag.posts, post)
                .orderBy(tag.id.asc())
                .groupBy(tag.id)
                .fetch();
    }
}
