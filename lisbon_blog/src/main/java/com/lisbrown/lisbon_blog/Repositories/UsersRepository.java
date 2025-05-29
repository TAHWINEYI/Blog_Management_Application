package com.lisbrown.lisbon_blog.Repositories;

import com.lisbrown.lisbon_blog.Entities.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @EntityGraph(attributePaths = {"posts"})
    List<Users> findAll();
    Optional<Users> findById(Long user_id);
    Users save(Users user);
    void deleteById(Long user_id);

    Users findByEmail(String email);
//    @Query(SELECT * FROM u,p )
//    List<Users> findByKeyword(String Keyword);


}
