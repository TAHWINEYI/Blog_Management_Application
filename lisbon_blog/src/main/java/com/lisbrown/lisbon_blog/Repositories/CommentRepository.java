package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> fetchAllComments();
    void saveComment(Comments comment);
    Comments fetchCommentById(Long comment_id);
    void deleteComment(Long comment_id);
}
