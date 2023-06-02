package com.myapp.blog.repository;

import com.myapp.blog.DTO.CategoryDTO;
import com.myapp.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {






}
