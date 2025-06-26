package com.lisbrown.lisbon_blog.Controllers;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;
import com.lisbrown.lisbon_blog.ServiceImpl.PostsServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
@EnableMethodSecurity
@EnableCaching
@Slf4j
@CrossOrigin(origins = "https://frontend.com")
public class PostsController {

    private final PostsServiceImpl postsService;

    public PostsController(PostsServiceImpl postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/posts")
    @Cacheable("posts")
    public ResponseEntity<Page<PostsDTO>> fetchAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {   log.info("fetching all posts");
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(postsService.findAllPosts(pageable));
    }

    @GetMapping("/{post_id}")
    @Cacheable("post")
    public ResponseEntity<Optional<PostsDTO>> fetchPostById(@PathVariable("post_id") Long post_id){
        log.info("fetching blog post with id: {}", post_id);
        return ResponseEntity.ofNullable(postsService.findPostById(post_id));
    }
    @PostMapping("/post")
    @PreAuthorize("hasRole('ADMIN','BLOGGER')")
    public ResponseEntity<Posts> newPost(@Valid @RequestBody PostsDTO postsDTO){
        Posts post = postsService.addNewPosts(postsDTO);
        log.info("creating a new post with title: {}", postsDTO.tittle());
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
    @DeleteMapping("/deletePost/{post_id}")
    @PreAuthorize("hasRole('ADMIN','BLOGGER')")
    public ResponseEntity<String> deletePost(@PathVariable("post_id") Long post_id){
        postsService.deletePosts(post_id);
        log.info("deleted blog post id: {}", post_id);
        return ResponseEntity.ok("post with id:" + post_id + "has been deleted");
    }

    @GetMapping("/search/{keyword}")
    @Cacheable("posts")
    public ResponseEntity<Page<PostsDTO>> search (@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  String keyword){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(postsService.fetchPostByKeyword(pageable,keyword));
    }
}
