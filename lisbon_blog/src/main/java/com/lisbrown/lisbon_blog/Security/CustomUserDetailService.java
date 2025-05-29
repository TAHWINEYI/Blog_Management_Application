package com.lisbrown.lisbon_blog.Security;

import com.lisbrown.lisbon_blog.Entities.Users;
import com.lisbrown.lisbon_blog.Repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public CustomUserDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email);
        if (user.getEmail().isEmpty())
            throw new UsernameNotFoundException("usee email doesn't exist");
        return new CustomUserDetails(user);
    }
}
