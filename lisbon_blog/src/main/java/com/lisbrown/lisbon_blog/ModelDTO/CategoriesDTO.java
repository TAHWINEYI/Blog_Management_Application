package com.lisbrown.lisbon_blog.ModelDTO;

import com.lisbrown.lisbon_blog.Entities.Posts;

public record CategoriesDTO(Long category_id
, String category, Posts post_id) {
}
