package com.example.blog.services;

import com.example.blog.model.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComment();

    List<Comment> getAllCommentById(Long postId);

    Comment getCommentById(Long commentId);

    boolean addNewComment(Comment comment);

    void deleteComment(Long commentId);

    void deleteAllByPostId(Long postId);

    @Transactional
    void updateComment(Comment comment);

    List<Comment> allCommentContains(String comment);
}
