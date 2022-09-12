package com.example.instapic.repository;

import com.example.instapic.Entity.Photo;
import com.example.instapic.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByUserId(Long id);
    Optional<Photo> findPostById(Long id);
}
