package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Entities.Categories;
import com.lisbrown.lisbon_blog.Services.CategoriesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Override
    public List<Categories> fetchAllCategories() {
        return List.of();
    }

    @Override
    public Categories fetchCategoryById(Long category_id) {
        return null;
    }

    @Override
    public void saveCategory(Categories category) {

    }

    @Override
    public void deleteCategory(Long category_id) {

    }
}
