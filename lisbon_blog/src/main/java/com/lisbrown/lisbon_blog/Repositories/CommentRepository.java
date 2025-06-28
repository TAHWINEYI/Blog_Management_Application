package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Comments;
import com.lisbrown.lisbon_blog.ModelDTO.CommentsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAll();
    Comments save(Comments comment);
    Optional<Comments> findById(Long comment_id);
    void deleteById(Long comment_id);

    @Query(value="SELECT c.content, c.created_date, c.user_id, c.post_id " +
            "FROM Comments c " +
            "WHERE c.content " +
            "LIKE '%:keyword%'", nativeQuery=true)
    List<CommentsDTO> findByKeyword(@Param("keyword") String keyword);
}
