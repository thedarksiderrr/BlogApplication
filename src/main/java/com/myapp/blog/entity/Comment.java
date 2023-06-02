package com.myapp.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content", length = 10000, nullable = false)
    private String content;

    private Date createdDate;

    //one post has many comments
    @ManyToOne
    @JoinColumn(name = "post_id")
//    @JsonIgnore
    private Post post;
}
