package org.duyhung.service;

import org.duyhung.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Page<Category> getAllCategories(Pageable pageable);

    Category getCategoryById(String id);

    Category saveCategory(Category category);

    void deleteCategory(String id);
}
