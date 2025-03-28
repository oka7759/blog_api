package oka_tech.blog.api.repository;

import oka_tech.blog.api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}
