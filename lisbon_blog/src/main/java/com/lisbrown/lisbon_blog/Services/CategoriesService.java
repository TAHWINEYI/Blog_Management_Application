package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Categories;

import java.util.List;

public interface CategoriesService {
    List<Categories> fetchAllCategories();
    Categories fetchCategoryById(Long category_id);
    void saveCategory(Categories category);
    void deleteCategory(Long category_id);
}
