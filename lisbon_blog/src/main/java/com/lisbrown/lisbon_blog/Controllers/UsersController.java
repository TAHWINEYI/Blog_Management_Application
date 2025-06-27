package com.lisbrown.lisbon_blog.Controllers;

import com.lisbrown.lisbon_blog.Entities.Users;
import com.lisbrown.lisbon_blog.ModelDTO.CreateUserDTO;
import com.lisbrown.lisbon_blog.ModelDTO.UsersDTO;
import com.lisbrown.lisbon_blog.Security.Permissions;
import com.lisbrown.lisbon_blog.ServiceImpl.UsersServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@EnableCaching
@Slf4j
@CrossOrigin(origins = "https://frontend.com")
@EnableMethodSecurity
public class UsersController {

    private final UsersServiceImpl usersService;


    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    @Cacheable(value ="users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UsersDTO>> fetchAllUsers(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10")int size)
    {
        Pageable pageable = PageRequest.of(page,size);
        log.info("fetching pages of users");
        Page<UsersDTO> users = usersService.fetchAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{user_id}")
    @Cacheable(value="user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<UsersDTO>> getUserById(@PathVariable("user_id") Long user_id){
        log.info("fetching user with user id {}:", user_id);
        return ResponseEntity
                .ok(usersService.fetchUserById(user_id));
    }

    @PostMapping("/register")
    public ResponseEntity<Users> newUser(@Valid @RequestBody CreateUserDTO user){
        Users createdUser = usersService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }
    @PutMapping("/update/{user_id}")
    @CachePut(value = "user")
    public ResponseEntity<Users> updateUsers(@Valid @RequestBody CreateUserDTO user,
                                             @PathVariable("user_id") Long user_id){
        log.info("updating user with user id: {}", user_id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usersService.updateUser(user,user_id));

    }
    @DeleteMapping("/delete/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "user")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long user_id){
        log.info("deleting user with user_id: {}", user_id );
        usersService.deleteUser(user_id);
        return ResponseEntity
                .ok("the user with id:" + user_id + "has been successfully deleted");
    }

    @PostMapping("/login")
    public String verify(@Valid @RequestBody Users user)
    {
        log.info("sending user details for verification");
        return usersService.verify(user);
    }

    @GetMapping("/search")
    @Cacheable(value = "users")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UsersDTO> search(@RequestParam(defaultValue = "0")int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam("keyword") String keyword){
        Pageable pageable = PageRequest.of(page,size);
        log.info("searching users using keyword: {}", keyword);
       return usersService.fetchByKeyword(pageable, keyword);
    }
}
