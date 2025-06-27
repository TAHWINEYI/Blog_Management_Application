package com.lisbrown.lisbon_blog.Security;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.Exceptions.ResourcesNotFoundException;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;
import com.lisbrown.lisbon_blog.Repositories.PostsRepository;
import com.lisbrown.lisbon_blog.ServiceImpl.PostsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Permissions {

    private final PostsServiceImpl postsService;

    public Permissions(PostsRepository postsRepository, PostsServiceImpl postsService) {
        this.postsService = postsService;

    }
    public boolean isOwner(Long postId, Authentication authentication){
        PostsDTO posts = postsService.findById(postId);
        User currentUser = (User) authentication.getPrincipal();
        return posts.user_id().getEmail().equals(currentUser.getUsername());
    }
}
