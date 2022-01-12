package com.example.blog.repository;

import com.example.blog.model.PostMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostMessage, Long> {

    List<PostMessage> findAllByUsersUserId(Long userId);

    PostMessage getPostMessageByPostBody(String postBody);

    List<PostMessage> getAllByPostBodyContains(String postBody);
}
