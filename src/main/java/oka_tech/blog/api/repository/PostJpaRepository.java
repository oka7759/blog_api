package oka_tech.blog.api.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oka_tech.blog.api.dto.PostDto;
import oka_tech.blog.api.dto.QPostDto;
import oka_tech.blog.api.dto.QPostDto_TagDto;

import oka_tech.blog.api.entity.Post;
import oka_tech.blog.api.exception.CustomException;
import oka_tech.blog.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;


import static oka_tech.blog.api.entity.QPost.post;
import static oka_tech.blog.api.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public PostDto findId(Long id) {
        Post postEntity = em.find(Post.class, id);
        if (postEntity != null) {
            postEntity.increaseViews();
            return queryFactory
                    .from(post)
                    .leftJoin(post.tags, tag)
                    .where(post.id.eq(id))
                    .transform(
                            GroupBy.groupBy(post.id).list(
                                    buildPostDto()
                            )
                    ).get(0);
        }else {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

    }

    public List<PostDto> findBySeriesId(Long id) {
        return queryFactory
                .from(post)
                .leftJoin(post.tags, tag)
                .where(post.series.id.eq(id))
                .transform(
                        GroupBy.groupBy(post.id).list(
                                buildPostDto()
                        )
                );
    }


    public Page<PostDto> searchPage(Pageable pageable) {
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
        JPAQuery<Long> count = queryFactory
                .select(post.count())
                .from(post);
        return PageableExecutionUtils.getPage(contents,pageable,count::fetchOne);
    }

    public List<PostDto> findPostByTitle(String title) {
        return queryFactory
                .from(post)
                .leftJoin(post.tags, tag)
                .where(post.title.eq(title))
                .transform(
                        GroupBy.groupBy(post.id).list(
                                buildPostDto()
                        )
                );
    }

    public List<PostDto> findPostByTagName(String tagName) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(post.tags, tag)
                .where(tag.name.eq(tagName))
                .transform(
                        GroupBy.groupBy(post.id).list(
                                buildPostDto()
                        )
                );
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



}

