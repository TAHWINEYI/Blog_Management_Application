package com.lisbrown.lisbon_blog.Controllers;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;
import com.lisbrown.lisbon_blog.ServiceImpl.PostsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostsController {

    private final PostsServiceImpl postsService;

    public PostsController(PostsServiceImpl postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostsDTO>> fetchAllPost(){
        return ResponseEntity.ok(postsService.findAllPosts());
    }
    @GetMapping("/posts/{post_id}")
    public ResponseEntity<Optional<PostsDTO>> fetchPostById(@PathVariable("post_id") Long post_id){
        return ResponseEntity.ofNullable(postsService.findPostById(post_id));
    }
    @PostMapping("/posts/newPost")
    public ResponseEntity<Posts> newPost(@Valid @RequestBody PostsDTO postsDTO){
        Posts post = postsService.addNewPosts(postsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
    @DeleteMapping("/posts/deletePost/{post_id}")
    public ResponseEntity<String> deletePost(@PathVariable("post_id") Long post_id){
        postsService.deletePosts(post_id);
        return ResponseEntity.ok("post with id:" + post_id + "has been deleted");
    }
}
