package com.lisbrown.lisbon_blog.ModelDTO;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.Entities.Users;

import java.time.Instant;

public record CommentsDTO(
                          String content,
                          Instant created_date,
                          Users user_id,
                          Posts post_id) {
}
