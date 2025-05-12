package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Configurations.AppConfig;
import com.lisbrown.lisbon_blog.Entities.Users;
import com.lisbrown.lisbon_blog.Exceptions.GlobalExceptionHandling;
import com.lisbrown.lisbon_blog.Exceptions.ResourcesNotFoundException;
import com.lisbrown.lisbon_blog.ModelDTO.CreateUserDTO;
import com.lisbrown.lisbon_blog.ModelDTO.UsersDTO;
import com.lisbrown.lisbon_blog.Repositories.UsersRepository;
import com.lisbrown.lisbon_blog.Services.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final GlobalExceptionHandling globalExceptionHandling;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, GlobalExceptionHandling globalExceptionHandling, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.globalExceptionHandling = globalExceptionHandling;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsersDTO> fetchAllUsers() {
        List<Users> users = usersRepository.findAll();
              return users.stream().map(user-> new UsersDTO(user.getUserId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getRole(),
                        user.getPosts(),
                        user.getComments()))
                        .collect(Collectors.toList());
    }

    @Override
    public Optional<UsersDTO> fetchUserById(Long user_id) throws ResourcesNotFoundException{
        return Optional.ofNullable(usersRepository.findById(user_id)
                .map(user -> new UsersDTO(user.getUserId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getRole(),
                        user.getPosts(),
                        user.getComments()))
                .orElseThrow(() -> new ResourcesNotFoundException("the user with id:" + user_id + "was not found")));
    }

    @Override
    public Users updateUser(CreateUserDTO updateDTO, Long user_id) {
        Optional<Users> updateUser = usersRepository.findById(user_id);
        if(updateUser.isPresent()){
            Users user = updateUser.get();
            user.setUserId(updateDTO.user_id());
            user.setFirstName(updateDTO.firstName());
            user.setLastName(updateDTO.lastName());
            user.setEmail(updateDTO.email());
            user.setCreatedAt(updateDTO.date_created());
            user.setRole(updateDTO.role());
            user.setPassword(this.passwordEncoder.encode(updateDTO.password()));
            user.setPasswordRetry(updateDTO.verify_password());
            usersRepository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public Users saveUser(CreateUserDTO NewUserDTO) {
        Users user = new Users();
      user.setUserId(NewUserDTO.user_id());
      user.setFirstName(NewUserDTO.firstName());
      user.setLastName(NewUserDTO.lastName());
      user.setEmail(NewUserDTO.email());
      user.setCreatedAt(NewUserDTO.date_created());
      user.setRole(NewUserDTO.role());
      user.setPassword(this.passwordEncoder.encode(NewUserDTO.password()));
      user.setPasswordRetry(NewUserDTO.verify_password());
      usersRepository.save(user);
      return user;
    }

    @Override
    public String deleteUser(Long user_id) {
        Optional<Users> deleteUser = usersRepository.findById(user_id);
        if(deleteUser.isPresent()){
            usersRepository.deleteById(user_id);
            return "user with user_id:" + user_id + "has been deleted successfully";
        };
        return "user with user_id:" + user_id + "was not found";
    }
}
