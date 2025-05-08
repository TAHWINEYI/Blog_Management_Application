package com.lisbrown.lisbon_blog.ModelDTO;

import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.Entities.Roles;

import java.time.Instant;
import java.util.List;

public record UsersDTO(Long user_id,
                       String firstName,
                       String lastName,
                       String email,
                       Instant date_created,
                       String password,
                       String password_retry,
                       Roles role,
                       List<Posts> post,
                       List<Comments> comments
                       ) {
}
