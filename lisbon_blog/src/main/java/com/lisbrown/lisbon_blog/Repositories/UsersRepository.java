package com.lisbrown.lisbon_blog.Repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository <Users, Long>{

    List<Users> fetchAllUsers();
    Users fetchUserById(Long user_id);
    void saveUser(Users user);
    void deleteUser(Long user_id);
}
