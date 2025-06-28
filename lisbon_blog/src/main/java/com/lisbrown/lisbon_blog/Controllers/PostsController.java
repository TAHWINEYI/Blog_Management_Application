package com.lisbrown.lisbon_blog.Controllers;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.ModelDTO.CategoriesDTO;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;
import com.lisbrown.lisbon_blog.Security.Permissions;
import com.lisbrown.lisbon_blog.ServiceImpl.CategoriesServiceImpl;
import com.lisbrown.lisbon_blog.ServiceImpl.PostsServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    private final Permissions permissions;
    private final CategoriesServiceImpl categoriesService;

    public PostsController(PostsServiceImpl postsService, Permissions permissions, CategoriesServiceImpl categoriesService) {
        this.postsService = postsService;
        this.permissions = permissions;
        this.categoriesService = categoriesService;
    }

    @GetMapping("/posts")
    @Cacheable("posts")
    public ResponseEntity<Page<PostsDTO>> fetchAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("fetching all posts");
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity
                .ok(postsService.findAllPosts(pageable));
    }

    @GetMapping("/{post_id}")
    @Cacheable("post")
    public ResponseEntity<Optional<PostsDTO>> fetchPostById(@PathVariable("post_id") Long post_id) {
        log.info("fetching blog post with id: {}", post_id);
        return ResponseEntity
                .ofNullable(postsService.findPostById(post_id));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('BLOGGER')")
    @Transactional(propagation =Propagation.REQUIRED)
    public ResponseEntity<Posts> newPost(@Valid @RequestBody PostsDTO postsDTO) {
        Posts post = postsService.addNewPosts(postsDTO);
        log.info("creating a new post with title: {}", postsDTO.tittle());
        return ResponseEntity
                .status(HttpStatus.CREATED).body(post);
    }

    @DeleteMapping("/delete/{post_id}")
    @PreAuthorize("hasRole('ADMIN','BLOGGER')")
    @CacheEvict(value = "post")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> deletePost(@PathVariable("post_id") Long post_id) {
        postsService.deletePosts(post_id);
        log.info("deleted blog post id: {}", post_id);
        return ResponseEntity
                .ok("post with id:" + post_id + "has been deleted");
    }

    @GetMapping("/category/{keyword}")
    @Cacheable(value="posts")
    public ResponseEntity<Page<PostsDTO>> search(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("searching for blogs with either title or content that contains keyword: {}", keyword);
        return ResponseEntity
                .ok(postsService.fetchPostByKeyword(pageable, keyword));
    }

    @PutMapping("/update/{post_id}")
    @CachePut(value ="updated")
    @PreAuthorize("hasRole('BLOGGER') and @permissions.isOwner(#post_id, authentication)")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Posts> updatePost(Long post_id) {
        PostsDTO updatePost = postsService.findById(post_id);
        log.info("updating blog post with id: {}",post_id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postsService.addNewPosts(updatePost));
    }

    @GetMapping("/post/{category}")
    @Cacheable(value="post")
    public ResponseEntity<Page<PostsDTO>> fetchByCategory(@RequestParam("category") String category,
                                                          @RequestParam(defaultValue="0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("fetching blog post under category: {}", category);
        return ResponseEntity.ofNullable(postsService.fetchByCategory(category, pageable));
    }
}
