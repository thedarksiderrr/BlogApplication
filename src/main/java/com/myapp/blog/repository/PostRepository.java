package com.myapp.blog.repository;

import com.myapp.blog.entity.Category;
import com.myapp.blog.entity.Post;
import com.myapp.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //to GET particular one user's all post
    Page<Post> findByUser(User user, Pageable pageable);

    //to GET particular one category's all post
    Page<Post> findByCategory(Category category, Pageable pageable);

    // to GET data by searching particular keyword
    List<Post> findByTitleContainingIgnoreCase(String title);
}
