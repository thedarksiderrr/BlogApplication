package com.myapp.blog.controller;

import com.myapp.blog.DTO.response.ApiResponse;
import com.myapp.blog.DTO.CategoryDTO;
import com.myapp.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("categoryId") Long categoryId) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

    //get by id
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryUsingId(@PathVariable("categoryId") Long categoryId) {
        CategoryDTO categoryById = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryById);
    }

    //get all
    @GetMapping("/")
    public ResponseEntity<List<?>> getAllCategories() {
        List<CategoryDTO> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);
    }

}
