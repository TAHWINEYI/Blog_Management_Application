package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;

import java.util.List;
import java.util.Optional;

public interface PostsService {
    List<PostsDTO> findAllPosts();
    Posts addNewPosts(PostsDTO post);
    String deletePosts(Long post_id);
    Optional<PostsDTO> findPostById(Long post_id);
}
