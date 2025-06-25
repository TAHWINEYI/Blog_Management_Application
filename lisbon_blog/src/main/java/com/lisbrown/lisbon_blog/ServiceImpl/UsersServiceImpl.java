package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Entities.Users;
import com.lisbrown.lisbon_blog.Exceptions.ResourcesNotFoundException;
import com.lisbrown.lisbon_blog.ModelDTO.CreateUserDTO;
import com.lisbrown.lisbon_blog.ModelDTO.UsersDTO;
import com.lisbrown.lisbon_blog.Repositories.UsersRepository;
import com.lisbrown.lisbon_blog.Services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(AuthenticationManager authenticationManager, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UsersDTO> fetchAllUsers(Pageable pageable) {
        Page<Users> users = usersRepository.findAll(pageable);
        List<UsersDTO> fetchedUserDTO = users.stream().map(user-> new UsersDTO(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getCreatedAt()))
                        .collect(Collectors.toList());
        return new PageImpl<>(fetchedUserDTO,pageable,fetchedUserDTO.size());
    }

    @Override
    public Optional<UsersDTO> fetchUserById(Long user_id) throws ResourcesNotFoundException{
        return Optional.ofNullable(usersRepository.findById(user_id)
                .map(user -> new UsersDTO(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getCreatedAt()
                ))
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
            user.setRoles(Collections.singleton(updateDTO.role()));
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
      user.setRoles(Collections.singleton(NewUserDTO.role()));
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

    public String verify(Users user) {
        Authentication authenticate = authenticationManager
                .authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        var u = usersRepository.findByEmail(user.getEmail());
        if(!Objects.isNull(u))
            return "login successful";
        return "login failed";
    }

    @Override
    public Page<UsersDTO> fetchByKeyword(Pageable pageable, String keyword) {
        String searchWord = keyword.toLowerCase();
        Page<Users> searchedResults = usersRepository.findByKeyword(searchWord,pageable);
        List<UsersDTO> fetchedUsersDTO = searchedResults.stream().map(sr-> new UsersDTO(
                sr.getFirstName(),
                sr.getLastName(),
                sr.getLastName(),sr.getCreatedAt()))
                .collect(Collectors.toList());
        return  new PageImpl<>(fetchedUsersDTO,pageable,fetchedUsersDTO.size());
    }
}
