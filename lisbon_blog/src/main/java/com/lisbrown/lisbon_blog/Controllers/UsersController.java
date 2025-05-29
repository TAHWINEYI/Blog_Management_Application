package com.lisbrown.lisbon_blog.Controllers;

import com.lisbrown.lisbon_blog.Entities.Users;
import com.lisbrown.lisbon_blog.ModelDTO.CreateUserDTO;
import com.lisbrown.lisbon_blog.ModelDTO.UsersDTO;
import com.lisbrown.lisbon_blog.ServiceImpl.UsersServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@EnableCaching
@Slf4j
@CrossOrigin
public class UsersController {

    private final UsersServiceImpl usersService;

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsersDTO>> fetchAllUsers(){
        List<UsersDTO> users = usersService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<Optional<UsersDTO>> getUserById(@PathVariable("user_id") Long user_id){
        return ResponseEntity.ok(usersService.fetchUserById(user_id));
    }

    @PostMapping("/users/register")
    public ResponseEntity<Users> newUser(@Valid @RequestBody CreateUserDTO user){
        Users createdUser = usersService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PutMapping("/users/updateUser/{user_id}")
    public ResponseEntity<Users> updateUsers(@Valid @RequestBody CreateUserDTO user, @PathVariable("user_id") Long user_id){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usersService.updateUser(user,user_id));

    }
    @DeleteMapping("/users/deleteUser/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long user_id){
        usersService.deleteUser(user_id);
        return ResponseEntity.ok("the user with id:" + user_id + "has been successfully deleted");
    }

    @PostMapping("/login")
    public String verify(@Valid @RequestBody Users user)
    {
        return usersService.verify(user);
    }
}
