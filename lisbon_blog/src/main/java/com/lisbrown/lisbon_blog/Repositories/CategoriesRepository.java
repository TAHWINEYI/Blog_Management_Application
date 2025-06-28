package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends PagingAndSortingRepository<Categories, Long> {
    List<Categories> findAll();
    Optional<Categories> findById(Long category_id);
    Categories save(Categories category);
    void deleteById(Long category_id);
}
