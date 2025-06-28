package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.Exceptions.GlobalExceptionHandling;
import com.lisbrown.lisbon_blog.Exceptions.ResourcesNotFoundException;
import com.lisbrown.lisbon_blog.ModelDTO.CommentsDTO;
import com.lisbrown.lisbon_blog.Repositories.CommentRepository;
import com.lisbrown.lisbon_blog.Services.CommentsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentRepository commentRepository;

    public CommentsServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentsDTO> findByKeyword(String keyword) {
        return commentRepository.findByKeyword(keyword)
                .stream()
                .toList();
    }

    @Override
    public List<CommentsDTO> findAllComments() {
            return commentRepository.findAll()
                    .stream()
                    .map(comment -> new CommentsDTO(
                             comment.getContent(),
                            comment.getCreatedOn(), comment.getUser(), comment.getPost()
                    )).toList();
    }

    @Override
    public Comments saveComment(CommentsDTO commentDTO) {
    Comments comments = new Comments();
    comments.setUser(commentDTO.user_id());
    comments.setContent(commentDTO.content());
    comments.setCreatedOn(commentDTO.created_date());
    comments.setPost(commentDTO.post_id());
    commentRepository.save(comments);
    return comments;
    }

    @Override
    public Optional<CommentsDTO> fetchCommentById(Long comment_id) {
        Optional<Comments> comments = commentRepository.findById(comment_id);
        return Optional.ofNullable(comments.map(comment -> new CommentsDTO(
                         comment.getContent(),
                        comment.getCreatedOn(),
                        comment.getUser(), comment.getPost()))
                .orElseThrow(() -> new ResourcesNotFoundException("comment with id:" + comment_id + "was not found")));
    }

    @Override
    public String deleteComment(Long comment_id) {
        Optional<Comments> comment = commentRepository.findById(comment_id);
        if(comment.isPresent()){
            Comments deleteComment = comment.get();
            commentRepository.deleteById(comment_id);
            return "comment with id" + comment_id + "has been successfully deleted";
        }
        return "cannot find comment with id" + comment_id;
    }

    @Override
    public Comments updateComment(CommentsDTO commentDTO, Long comment_id) {
        return null;
    }
}
