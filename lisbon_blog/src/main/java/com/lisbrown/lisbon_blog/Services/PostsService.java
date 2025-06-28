package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PostsService {
    Page<PostsDTO> findAllPosts(Pageable pageable);
    Posts addNewPosts(PostsDTO post);
    String deletePosts(Long post_id);
    Optional<PostsDTO> findPostById(Long post_id);
    Page<PostsDTO> fetchPostByKeyword(Pageable pageable, String keyowrd);
    PostsDTO findById(Long postId);
    Page<PostsDTO> fetchByCategory(String category, Pageable pageable);
}
