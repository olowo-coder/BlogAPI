package com.example.blog.services;

import com.example.blog.model.Comment;
import com.example.blog.model.LikeComment;
import com.example.blog.model.LikePost;
import com.example.blog.model.PostMessage;

import java.util.List;

public interface LikeService {
    LikePost checkIfExists(Long postId);

    boolean addLikePost(LikePost likePost);

    void deleteLikePost(Long likePostId);

    void updateLikePost(LikePost likePost);

    List<LikePost> getAllLikesOfPost();

    void addPost(PostMessage postMessage);

    LikeComment getCommentLikeById(Long commetId);

    void addCommentLike(Comment comment);

    void updateLikeComment(LikeComment likeComment);
}
