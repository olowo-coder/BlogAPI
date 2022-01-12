package com.example.blog.repository;

import com.example.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
//    @Query("select s from Comment s where s.postMessage.postId = ?1")
//    List<Comment> findByPostMessageId(Long postId);

    List<Comment> findByPostMessagePostId(Long postid);

    int deleteAllByPostMessagePostId(Long postId);

    List<Comment> getAllByCommentBodyContains(String comment);
}
