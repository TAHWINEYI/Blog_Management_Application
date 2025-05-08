package com.lisbrown.lisbon_blog.ModelDTO;

import com.lisbrown.lisbon_blog.Entities.Categories;
import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.Entities.Users;

import java.sql.Blob;
import java.time.Instant;
import java.util.List;

public record PostsDTO(Long post_id,
                       String tittle,
                       String content,
                       Categories category_id,
                       Users user_id,
                       Instant created_date,
                       Blob image,
                       List<Comments> comments) {
}
