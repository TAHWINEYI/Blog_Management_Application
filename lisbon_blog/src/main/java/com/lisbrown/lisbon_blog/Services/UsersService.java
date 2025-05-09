package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Users;

import java.util.List;

public interface UsersService {
    List<Users> fetchAllUsers();
    Users fetchUserById(Long user_id);
    void saveUser(Users user);
    void deleteUser(Long user_id);
}
