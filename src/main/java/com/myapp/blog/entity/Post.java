package com.myapp.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @Column(name = "post_content", length = 10000, nullable = false)
    private String content;

    @Column(name = "post_image")
    private String image="default.png";

    @Column(name = "created_date")
    private Date date;

    //multiple post can have one category........
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id", nullable = false)
//    @JsonBackReference
//    @JsonIgnore
    private Category category;

    //multiple post can own by one user.........
    @ManyToOne
    @JoinColumn(nullable = false)
//    @JsonIgnore
    private User user;

    //one post has many comments............
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "comment_id")
    private Set<Comment> comment = new HashSet<>();

}
