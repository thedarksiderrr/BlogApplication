package com.myapp.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(length = 100, nullable = false)
    private String categoryTitle;

    @Column(length = 500, nullable = false)
    private String categoryDescription;

    //one category can have many posts.....
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //cascade all means if we drop our parent table then this one should also go to drop.
//    @JsonManagedReference
//    @JsonIgnore
    private List<Post> post = new ArrayList<>();
}
