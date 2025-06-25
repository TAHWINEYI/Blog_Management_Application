package com.lisbrown.lisbon_blog.ModelDTO;

import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.Entities.Roles;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record UsersDTO(
                       String firstName,
                       String lastName,
                       String email,
                       Instant date_created
                       ) {
}
