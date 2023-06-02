package com.myapp.blog.service;

import com.myapp.blog.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {

    //create category
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //update category
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

    //delete category
    void deleteCategory(Long categoryId);

    //get all category
    List<CategoryDTO> getAllCategory();

    //get category by id
    CategoryDTO getCategoryById(Long categoryId);

}
