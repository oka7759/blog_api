package oka_tech.blog.api.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import oka_tech.blog.api.dto.PostDto;
import oka_tech.blog.api.dto.QPostDto;
import oka_tech.blog.api.dto.QPostDto_TagDto;

import oka_tech.blog.api.entity.QSeries;
import oka_tech.blog.api.entity.Series;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static oka_tech.blog.api.entity.QPost.post;
import static oka_tech.blog.api.entity.QSeries.*;
import static oka_tech.blog.api.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class PostJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostDto findId(Long id) {
        List<PostDto> results = queryFactory
                .from(post)
                .leftJoin(post.tags, tag)
                .where(post.id.eq(id))
                .transform(
                        GroupBy.groupBy(post.id)
                                .list(buildPostDto())
                );
        return results.isEmpty() ? null : filterNullTags(results.get(0));
    }


    public PageImpl<PostDto> searchPage(Pageable pageable) {
        List<PostDto> contents = queryFactory
                .from(post)
                .leftJoin(post.tags, tag)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .transform(
                        GroupBy.groupBy(post.id).list(
                                buildPostDto()
                        )
                );

        contents.forEach(this::filterNullTags);
        long total = queryFactory
                .select(post.count())
                .from(post)
                .fetchCount();
        return new PageImpl<>(contents, pageable, total);
    }

    private QPostDto buildPostDto() {
        return new QPostDto(
                post.id,
                post.title,
                post.content,
                post.views,
                post.imageUrl,
                post.description,
                post.createdAt,
                GroupBy.list(new QPostDto_TagDto(tag.id, tag.name))
        );
    }


    private PostDto filterNullTags(PostDto postDto) {
        if (postDto == null) {
            return null;
        }
        List<PostDto.TagDto> tags = postDto.getTags();
        if (tags == null) {
            postDto.setTags(new ArrayList<>());
        } else {
            postDto.setTags(
                    tags.stream()
                            .filter(tagDto -> tagDto.getTagId() != null && tagDto.getName() != null)
                            .collect(Collectors.toList())
            );
        }
        return postDto;
    }


}

