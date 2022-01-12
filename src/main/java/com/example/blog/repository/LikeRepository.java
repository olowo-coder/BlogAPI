package com.example.blog.repository;

import com.example.blog.model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikePost, Long> {
    LikePost getLikePostByPostMessagePostId (Long postId);

    void deleteLikePostByPostMessagePostId(Long postId);
}
