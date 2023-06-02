package com.myapp.blog.service.impl;

import com.myapp.blog.DTO.CategoryDTO;
import com.myapp.blog.entity.Category;
import com.myapp.blog.exceptions.ResourceNotFoundException;
import com.myapp.blog.repository.CategoryRepository;
import com.myapp.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category categoryEntity = dtoToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(categoryEntity);

        return entityToDto(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category updatedCategory = categoryRepository.save(category);
        CategoryDTO categoryDTO1 = entityToDto(updatedCategory);

        return categoryDTO1;
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {

        List<Category> allCategories = categoryRepository.findAll();

        List<CategoryDTO> list = allCategories.stream()
                .map(category -> entityToDto(category))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return entityToDto(category);
    }

    //----------------------------------------------------------------------------
    private Category dtoToEntity(CategoryDTO categoryDTO) {
        Category categoryEntity = mapper.map(categoryDTO, Category.class);
        return categoryEntity;
    }

    private CategoryDTO entityToDto(Category categoryEntity) {
        CategoryDTO categoryDTO = mapper.map(categoryEntity, CategoryDTO.class);
        return categoryDTO;
    }
}
