package com.example.blog.services;

import com.example.blog.model.FavoritePost;
import com.example.blog.model.PostMessage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    List<PostMessage> getAllPost();

    Page<PostMessage> pageGetAllPosts(String byWhat, int next);

    boolean addNewPost(PostMessage postMessage);

    PostMessage getPostById(Long postId);

    void updatePost(PostMessage postMessage);

    void deletePost(Long postId);

    List<PostMessage> getAllPostById(Long userId);

    PostMessage getPostByPostBody(String postBody);

    void favoritePost(Long postId);

    List<FavoritePost> getAllFavoritePost();

    List<FavoritePost> getFavoritePostByUser(Long userId);

    List<PostMessage> allPostContains(String post);
}
