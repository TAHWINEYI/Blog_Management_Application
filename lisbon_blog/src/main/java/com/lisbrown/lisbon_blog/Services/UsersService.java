package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Users;
import com.lisbrown.lisbon_blog.ModelDTO.CreateUserDTO;
import com.lisbrown.lisbon_blog.ModelDTO.UsersDTO;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<UsersDTO> fetchAllUsers();
    Optional<UsersDTO> fetchUserById(Long user_id);
    Users updateUser(CreateUserDTO updateUser, Long user_id);
    String deleteUser(Long user_id);
    void saveUser(CreateUserDTO NewUserDTO);
}
