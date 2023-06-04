package org.duyhung.service;

import org.duyhung.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(String id);

    Category saveCategory(Category category);

    void deleteCategory(String id);
}
