package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Posts;

import java.util.List;

public interface PostsService {
    List<Posts> findAllPosts();
    void savePosts(Posts post);
    void deletePosts(Long post_id);
    Posts findPostById(Long post_id);
}
