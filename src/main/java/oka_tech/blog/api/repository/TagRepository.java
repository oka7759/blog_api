package oka_tech.blog.api.repository;

import oka_tech.blog.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {}
