package oka_tech.blog.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static oka_tech.blog.api.entity.QTag.*;

@Repository
@RequiredArgsConstructor
public class TagJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<String> getTags(){
        return queryFactory
                .select(tag.name)
                .from(tag).
                orderBy(tag.id.asc())
                .fetch();
    }
}
