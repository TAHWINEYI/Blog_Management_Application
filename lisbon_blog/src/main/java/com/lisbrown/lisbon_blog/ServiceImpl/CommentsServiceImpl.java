package com.lisbrown.lisbon_blog.ServiceImpl;

import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.Services.CommentsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Override
    public List<Comments> findAllComments() {
        return List.of();
    }

    @Override
    public void saveComment(Comments comment) {

    }

    @Override
    public Comments fetchCommentById(Long comment_id) {
        return null;
    }

    @Override
    public void deleteComment(Long comment_id) {

    }
}
