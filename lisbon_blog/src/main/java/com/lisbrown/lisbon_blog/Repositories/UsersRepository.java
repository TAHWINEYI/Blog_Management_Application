package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, Long> {

    @EntityGraph(attributePaths = {"posts"})
    Page<Users> findAll(Pageable pageable);
    Optional<Users> findById(Long user_id);
    Users save(Users user);
    void deleteById(Long user_id);

    Users findByEmail(String email);

    @Query(value="SELECT * " +
            "FROM Users u " +
            "WHERE u.email " +
            "OR u.first_name " +
            "OR u.last_name " +
            "LIKE '%:keyword%'", nativeQuery = true)
    Page<Users> findByKeywordIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
}
