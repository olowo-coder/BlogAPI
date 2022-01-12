package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.services.CommentService;
import com.example.blog.services.LikeService;
import com.example.blog.services.PostService;
import com.example.blog.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("blog/user")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likePostService;
    private final UsersService usersService;


    @Autowired
    public PostController(PostService postService, CommentService commentService, LikeService likePostService, UsersService usersService) {
        this.postService = postService;
        this.commentService = commentService;
        this.likePostService = likePostService;
        this.usersService = usersService;
    }



    @PostMapping("/{userId}/post")
    public ResponseEntity<?> addPost(@RequestBody PostMessage postMessage, @PathVariable Long userId, HttpServletRequest request){
        HttpSession session = request.getSession();
//        Users user = (Users) session.getAttribute("user");
        postMessage.setUsers(usersService.getUserById(userId));
        postMessage.setTimeStamp(LocalDateTime.now());
        postService.addNewPost(postMessage);
        likePostService.addPost(postService.getPostByPostBody(postMessage.getPostBody()));
        return new ResponseEntity<>("User added", HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/post")
    public ResponseEntity<?> getAllUserPost(@PathVariable Long userId, HttpServletRequest request){
        return ResponseEntity.ok().body(postService.getAllPostById(userId));
    }

    @PutMapping("/{userId}/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostMessage postMessage){
//        if(Integer.toString(Math.toIntExact(postId)).equals("all")){
//            return ResponseEntity.ok().body(postService.getAllPost());
//        }
        PostMessage existingPost = postService.getPostById(postId);
        existingPost.setPostBody(postMessage.getPostBody());
        existingPost.setPostId(postMessage.getPostId());
        postService.updatePost(existingPost);
        return ResponseEntity.ok().body("Updated");
    }

    @DeleteMapping("/{userId}/post/{postId}")
    public ResponseEntity<?> removeComment(@PathVariable Long userId, @PathVariable Long postId){
        likePostService.deleteLikePost(postId);
        commentService.deleteAllByPostId(postId);
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{userId}/post/favorite")
    public ResponseEntity<?> getUserFavoritePost(@RequestBody PostMessage postMessage,
                                          @PathVariable Long userId,
                                          HttpServletRequest request){
        HttpSession session = request.getSession();
        postService.getFavoritePostByUser(userId);
        return new ResponseEntity<>(" added", HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/post/{postId}/favorite")
    public ResponseEntity<?> addFavoritePost(@RequestBody PostMessage postMessage,
                                          @PathVariable Long userId,
                                          @PathVariable Long postId,
                                          HttpServletRequest request){
        HttpSession session = request.getSession();
        postService.favoritePost(postId);
        return new ResponseEntity<>("Favorite Post added", HttpStatus.CREATED);
    }

}
