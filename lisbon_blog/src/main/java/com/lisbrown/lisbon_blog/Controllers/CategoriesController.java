package com.lisbrown.lisbon_blog.Controllers;

import com.lisbrown.lisbon_blog.Entities.Categories;
import com.lisbrown.lisbon_blog.ModelDTO.CategoriesDTO;
import com.lisbrown.lisbon_blog.ServiceImpl.CategoriesServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@Slf4j
@EnableCaching
@EnableMethodSecurity
public class CategoriesController {
    private final CategoriesServiceImpl categoriesService;

    public CategoriesController(CategoriesServiceImpl categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/categories")
    @Cacheable(value="categories")
    public ResponseEntity<Page<CategoriesDTO>> fetchCategories(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue="10") int size){
        Pageable pageable = PageRequest.of(page, size);
        log.info("fetching categories in pages of size of 10");
        List<CategoriesDTO> categories = categoriesService.fetchAllCategories();
       return ResponseEntity
               .ok(new PageImpl<>(categories,pageable,categories.size()));
    }

    @GetMapping("/categories/{categoryId}")
    @Cacheable(value="category")
    public ResponseEntity<CategoriesDTO> getCategoryById(@RequestParam("categoryId") Long categoryId){
        Optional<CategoriesDTO> optCategory = categoriesService.fetchCategoryById(categoryId);
        log.info("fetching category with id {}", categoryId);
        return optCategory
                .map(ResponseEntity::ok)
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/update/category/{categoryId}")
    @CachePut(value="category")
    @PreAuthorize("hasRole('BLOGGER')")
    public ResponseEntity<?> updateCategory(@RequestParam("categoryId") Long categoryId){
        Optional<CategoriesDTO> updateCategory = categoriesService.fetchCategoryById(categoryId);
        log.info("updating category with id {}", categoryId);
        if(updateCategory.isPresent()){
            return ResponseEntity
                    .ok(categoriesService.saveCategory(updateCategory.get()));
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole(ADMIN)")
    public ResponseEntity<Categories> newCategory (@Valid @RequestBody CategoriesDTO categoriesDTO){
        log.info("creating new category");
        return ResponseEntity.ok(categoriesService.saveCategory(categoriesDTO));
    }
    
    @DeleteMapping("/delete/{categoryId}")
    @CacheEvict(value="category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletingCategory(@RequestParam("categoryId") Long categoryId){
       categoriesService.deleteCategory(categoryId);
       return ResponseEntity.ok("Category with category id:" + categoryId + "was deleted successfully");
    }
    
    
}
