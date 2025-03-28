package oka_tech.blog.api.repository;

import oka_tech.blog.api.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    List<Series> findAllByOrderByCreatedAtDesc();

}
