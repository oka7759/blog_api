package oka_tech.blog.api.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag",schema = "blog2")
@Getter
public class Tag extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();


}
