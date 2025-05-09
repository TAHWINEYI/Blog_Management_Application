package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    List<Categories> fetchAllCategories();
    Categories fetchCategoryById(Long category_id);
    void saveCategory(Categories category);
    void deleteCategory(Long category_id);
}
