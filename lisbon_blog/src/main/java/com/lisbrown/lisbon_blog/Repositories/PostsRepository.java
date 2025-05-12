package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findAll();
    Posts save(Posts post);
    void deleteById(Long post_id);
    Optional<Posts> findById(Long post_id);
}
