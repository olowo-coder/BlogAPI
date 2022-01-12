package com.example.blog.controller;

import com.example.blog.model.LikeComment;
import com.example.blog.model.LikePost;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.services.LikeService;
import com.example.blog.services.PostService;
import com.example.blog.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/blog/user/{userId}")
public class LikeController {

    private final LikeService likeService;
    private final PostService postService;
    private final UsersService usersService;

    @Autowired
    public LikeController(LikeService likeService, PostService postService, UsersService usersService) {
        this.likeService = likeService;
        this.postService = postService;
        this.usersService = usersService;
    }


    @GetMapping("/post/{postId}/like")
    public ResponseEntity<?> getLikesOfPost(@PathVariable Long postId){
        return new ResponseEntity<>(likeService.checkIfExists(postId), HttpStatus.OK);
    }

    @PutMapping("/post/{postId}/like")
    public ResponseEntity<?> updateLikePost(@RequestBody LikePost likePost,
                                         @PathVariable Long postId,
                                         @PathVariable Long userId,
                                         HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user", usersService.getUserById(userId));
        PostMessage postMessage = postService.getPostById(postId);
        LikePost likePost1 = likeService.checkIfExists(postId);

        if(likePost1 == null){
            likePost.setPLike(1L);
            likePost.setPostMessage(postMessage);
            likeService.addLikePost(likePost);
        }
        else {
            if(likePost1.getPLike() == 1) likePost.setPLike(0L);
            else likePost.setPLike(1L);
            likeService.updateLikePost(likePost);
        }
        return ResponseEntity.ok().body("Updated");
    }

    @GetMapping("/comment/{commentId}/like")
    public ResponseEntity<?> getLikesOfComment (@PathVariable Long commentId){
        return new ResponseEntity<>(likeService.getCommentLikeById(commentId), HttpStatus.OK);
    }

    @PutMapping("/comment/{commentId}/like")
    public ResponseEntity<?> updateLikeComment(@RequestBody LikeComment likeComment,
                                            @PathVariable Long commentId,
                                            @PathVariable Long userId,
                                            HttpServletRequest request){


            LikeComment likeComment1 = likeService.getCommentLikeById(commentId);
            if(likeComment1.getCLike() == 1) likeComment1.setCLike(0L);
            else likeComment1.setCLike(1L);
            likeService.updateLikeComment(likeComment);

        return ResponseEntity.ok().body("Updated");
    }



}
