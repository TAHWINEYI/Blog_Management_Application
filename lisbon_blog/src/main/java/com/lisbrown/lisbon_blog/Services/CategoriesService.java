package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Categories;
import com.lisbrown.lisbon_blog.ModelDTO.CategoriesDTO;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
    List<CategoriesDTO> fetchAllCategories();
    Optional<CategoriesDTO> fetchCategoryById(Long category_id);
    Categories saveCategory(CategoriesDTO categoryDTO);
    String deleteCategory(Long category_id);
}
