package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAll();
    Comments save(Comments comment);
    Optional<Comments> findById(Long comment_id);
    void deleteById(Long comment_id);
}
