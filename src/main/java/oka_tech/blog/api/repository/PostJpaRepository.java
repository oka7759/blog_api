package oka_tech.blog.api.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oka_tech.blog.api.dto.PostDto;

import oka_tech.blog.api.entity.Post;
import oka_tech.blog.api.exception.CustomException;
import oka_tech.blog.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.Function;


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
            return new PostDto(postEntity);
        }else {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

    }

    public List<PostDto> findBySeriesId(Long id) {
        List<Post> result = queryFactory
                .selectFrom(post)
                .leftJoin(post.tags, tag).fetchJoin()
                .where(post.series.id.eq(id)).fetch();

        return tagMapper(result,PostDto::new);
    }


    public Page<PostDto> searchPage(Pageable pageable) {
        List<Post> result = queryFactory
                .selectFrom(post)
                .leftJoin(post.tags, tag).fetchJoin()
                .groupBy(post.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        List<PostDto> postDos = tagMapper(result,PostDto::new);

        JPAQuery<Long> count = queryFactory
                .select(post.count())
                .from(post);
        return PageableExecutionUtils.getPage(postDos,pageable,count::fetchOne);
    }

    public List<PostDto> findPostByTitle(String title) {
        List<Post> result = queryFactory
                .selectFrom(post)
                .leftJoin(post.tags, tag).fetchJoin()
                .where(post.title.likeIgnoreCase("%" + title + "%")).fetch();

        return tagMapper(result,PostDto::new);

    }

    public List<PostDto> findPostByTagName(String tagName) {
        List<Post> result = queryFactory
                .selectFrom(post)
                .leftJoin(post.tags, tag).fetchJoin()
                .where(tag.name.eq(tagName)).fetch();

        return tagMapper(result,PostDto::new);

    }

    private <T, R> List<R> tagMapper(List<T> list, Function<T, R> mapper) {
        return list.stream()
                .map(mapper)
                .toList();
    }




}

