package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.ModelDTO.CommentsDTO;

import java.util.List;
import java.util.Optional;

public interface CommentsService {
    List<CommentsDTO> findAllComments();
    Comments updateComment(CommentsDTO commentDTO, Long comment_id);
    Comments saveComment(CommentsDTO commentDTO);
    Optional<CommentsDTO> fetchCommentById(Long comment_id);
    String deleteComment(Long comment_id);
    List<CommentsDTO> searchByKeyword(String keyword);
}
