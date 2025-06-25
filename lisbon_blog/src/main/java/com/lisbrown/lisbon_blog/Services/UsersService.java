package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Users;
import com.lisbrown.lisbon_blog.ModelDTO.CreateUserDTO;
import com.lisbrown.lisbon_blog.ModelDTO.UsersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    Page<UsersDTO> fetchAllUsers(Pageable pageable);
    Optional<UsersDTO> fetchUserById(Long user_id);
    Users updateUser(CreateUserDTO updateUser, Long user_id);
    String deleteUser(Long user_id);
    Users saveUser(CreateUserDTO NewUserDTO);
    Page<UsersDTO> fetchByKeyword(Pageable pageable,
                                  @Param("keyword") String keyword);
}
