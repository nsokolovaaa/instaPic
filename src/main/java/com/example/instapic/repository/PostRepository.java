package com.example.instapic.repository;

import com.example.instapic.Entity.Post;
import com.example.instapic.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
     List<Post> findAllByOrderByCreatedDateDesc(Users users);
     List<Post> findAllByUsersOrderByCreatedDateDesc();
    Optional<Post> findPostByIdAAndUsers(Long id, Users users);
}
