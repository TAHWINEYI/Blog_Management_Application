package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Posts;
import com.lisbrown.lisbon_blog.ModelDTO.PostsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends PagingAndSortingRepository<Posts, Long> {

    Page<Posts> findAll(Pageable pageable);
    Posts save(Posts post);
    void deleteById(Long post_id);
    Optional<Posts> findById(Long post_id);
    Posts findByPostId(Long postId);

    @Query(value="SELECT p.tittle, p.content, p,category_id, p.user_id, p.created_date, p.image, p.comments " +
            "FROM Posts p " +
            "WHERE p.tittle " +
            "OR p.content " +
            "LIKE '%:keyword%'", nativeQuery=true)
    Page<Posts> findByKeywordIgnoreCase(Pageable pageable,String keyword);
}
