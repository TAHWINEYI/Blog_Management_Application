package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Posts;

import java.util.List;

public interface PostsService {
    List<Posts> fetchAllPosts();
    void savePosts(Posts post);
    void deletePosts(Long post_id);
    Posts fetchPostById(Long post_id);
}
