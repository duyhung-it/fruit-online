package org.duyhung.service.impl;

import org.duyhung.entity.Category;
import org.duyhung.repository.CategoryRepository;
import org.duyhung.service.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category saveCategory(Category category) {
        if(category.getId().isEmpty()) category.setId(null);
        category.setCreatedDate(LocalDateTime.now());
        category.setUpdatedDate(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = new Category();
        category.setId(id);
        categoryRepository.delete(category);
    }
}
