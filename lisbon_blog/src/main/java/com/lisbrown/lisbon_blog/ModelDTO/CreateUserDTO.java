package com.lisbrown.lisbon_blog.ModelDTO;

import com.lisbrown.lisbon_blog.Entities.Roles;

import java.time.Instant;

public record CreateUserDTO(Long user_id,
                            String firstName,
                            String lastName,
                            String email,
                            Instant date_created,
                            Roles role,
                            String password,
                            String verify_password) {
}
