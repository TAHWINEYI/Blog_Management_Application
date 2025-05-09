package com.lisbrown.lisbon_blog.Services;

import com.lisbrown.lisbon_blog.Entities.Comments;

import java.util.List;

public interface CommentsService {
    List<Comments> fetchAllComments();
    void saveComment(Comments comment);
    Comments fetchCommentById(Long comment_id);
    void deleteComment(Long comment_id);
}
