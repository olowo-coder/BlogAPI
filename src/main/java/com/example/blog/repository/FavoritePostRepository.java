package com.example.blog.repository;

import com.example.blog.model.FavoritePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {
    List<FavoritePost> getAllByPostMessageUsersUserId(Long userId);
}
