package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Entities.Categories;
import com.lisbrown.lisbon_blog.Exceptions.ResourcesNotFoundException;
import com.lisbrown.lisbon_blog.ModelDTO.CategoriesDTO;
import com.lisbrown.lisbon_blog.Repositories.CategoriesRepository;
import com.lisbrown.lisbon_blog.Services.CategoriesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<CategoriesDTO> fetchAllCategories() {
        return categoriesRepository
                .findAll()
                .stream()
                .map(category-> new CategoriesDTO(category.getCategory_id(),
                        category.getCategory(),
                        category.getPost())).sorted().collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriesDTO> fetchCategoryById(Long category_id) {
        Optional<Categories> categories = categoriesRepository.findById(category_id);
       return Optional.ofNullable(categories.map(categories1 -> new CategoriesDTO(
               categories1.getCategory_id(),
               categories1.getCategory(),
               categories1.getPost()
       )).orElseThrow(() -> new ResourcesNotFoundException("requested category doesnt exist")));
    }

    @Override
    public Categories saveCategory(CategoriesDTO categoryDTO) {
        Categories categories = new Categories();
        categories.setCategory_id(categoryDTO.category_id());
        categories.setCategory(categoryDTO.category());
        categories.setPost(categoryDTO.post_id());
        categoriesRepository.save(categories);
        return categories;
    }

    @Override
    public String deleteCategory(Long category_id) {
      Optional<Categories> categories = categoriesRepository.findById(category_id);
      if(categories.isPresent()){
          categoriesRepository.deleteById(category_id);
          return "category of category id:" + category_id + "successfully deleted";
      }
      return "category with category id :" + category_id + "was not found";
    }
}
