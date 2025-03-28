package oka_tech.blog.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "post_tag", schema = "blog2")
public class PostTag {
    @EmbeddedId
    private PostTagId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId") // PostTagId의 postId 필드와 매핑
    @JoinColumn(name = "post_id")
    private Post post;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId") // PostTagId의 tagId 필드와 매핑
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
