package oka_tech.blog.api.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "series", schema = "blog2")
@Getter
public class Series extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
    private List<Post> posts;

    private String description;


}
