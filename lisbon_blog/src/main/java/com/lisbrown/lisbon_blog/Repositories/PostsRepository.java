package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Posts;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository <Posts, Long>{

    List<Posts> fetchAllPosts();
    void savePosts(Posts post);
    void deletePosts(Long post_id);
    Posts fetchPostById(Long post_id);
}
