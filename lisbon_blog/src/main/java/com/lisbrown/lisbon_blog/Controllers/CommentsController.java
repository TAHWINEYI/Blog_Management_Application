package com.lisbrown.lisbon_blog.Controllers;

import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.ModelDTO.CommentsDTO;
import com.lisbrown.lisbon_blog.ServiceImpl.CommentsServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins="https//:frontend.com")
@EnableCaching
@EnableMethodSecurity
@Slf4j
@Controller
@RequestMapping("/api")
public class CommentsController {

    private final CommentsServiceImpl commentsService;
    //private final Permissions permissions;

    public CommentsController(CommentsServiceImpl commentsService) {
        this.commentsService = commentsService;
       // this.permissions = permissions;
    }

    @GetMapping("/comments")
    @Cacheable(value="comments")
    public ResponseEntity<Page<CommentsDTO>> fetchComments(@RequestParam(defaultValue=("0")) int page,
                                                        @RequestParam(defaultValue =("10")) int size)
    {
       Pageable pageable = PageRequest.of(page, size);
       List<CommentsDTO> comments = commentsService.findAllComments()
               .stream()
               .map(comment-> new CommentsDTO(comment.content(),
                       comment.created_date(),
                       comment.user_id(),
                       comment.post_id()))
               .toList();
               log.info("fetching pages of comments");
               return ResponseEntity
                       .ok(new PageImpl<>(comments, pageable,comments.size()));
    }
    @GetMapping("comments/{commentId}")
    @CachePut(value="comment")
    public ResponseEntity<CommentsDTO> fetchById(@PathVariable("commentId") Long commentId){
        Optional<CommentsDTO> OptionalComment = commentsService.fetchCommentById(commentId);
        log.info("fetching comment with id: {}", commentId);
        return OptionalComment
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/update/{commentId}")
    @CachePut("comment")
    @Transactional(propagation = Propagation.REQUIRED)
    @PreAuthorize("hasRole('BLOGGER,READER') and @permissions.isOwner(#commentId, authentication)")
    public ResponseEntity<Optional<CommentsDTO>> updateComment(@PathVariable("commentId") Long commentId){
        Optional<CommentsDTO> comment = commentsService.fetchCommentById(commentId);
        if(comment.isPresent()){
            log.info("updating comment with id:{}",commentId);
            commentsService.saveComment(comment.get());
            return ResponseEntity.ok(comment);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("/save/comment")
    @PreAuthorize("hasRole('BLOGGER,READER')")
    public ResponseEntity<Comments> save(@Valid @RequestBody CommentsDTO commentsDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentsService.saveComment(commentsDTO));
    }

    @DeleteMapping("delete/{commentId}")
    @CacheEvict
    @Transactional(propagation = Propagation.REQUIRED)
    @PreAuthorize("hasRole('BLOGGER,READER') and @permissions.isOwner(#commentId, authentication)")
    public ResponseEntity<String> delete(@PathVariable("commentId") Long commentId){
        log.info("deleting comment with id: {}", commentId);
        commentsService.deleteComment(commentId);
        return ResponseEntity.ok("user with id: " + commentId + "has been deleted successfully");
    }

    @GetMapping("/search")
    @Cacheable(value = "comments")
    public ResponseEntity<PageImpl<CommentsDTO>> search (String keyword,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size){
       Pageable pageable = PageRequest.of(page, size);
       List<CommentsDTO> results = commentsService.searchByKeyword(keyword);
       return ResponseEntity.ok(new PageImpl<>(results,pageable,results.size()));
    }
}
