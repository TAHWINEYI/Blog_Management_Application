package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.Services.PostsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsServiceImpl implements PostsService {
    @Override
    public List<Posts> findAllPosts() {
        return List.of();
    }

    @Override
    public void savePosts(Posts post) {

    }

    @Override
    public void deletePosts(Long post_id) {

    }

    @Override
    public Posts findPostById(Long post_id) {
        return null;
    }
}

