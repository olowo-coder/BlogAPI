package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.PostMessage;
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
@RequestMapping("/blog/user/{userId}/post")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UsersService usersService;
    private final LikeService likeService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UsersService usersService, LikeService likeService) {
        this.commentService = commentService;
        this.postService = postService;
        this.usersService = usersService;
        this.likeService = likeService;
    }



    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> saveComment(@RequestBody Comment comment,
                              @PathVariable Long postId, @PathVariable Long userId, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user", usersService.getUserById(userId));
        PostMessage postMessage = postService.getPostById(postId);
        comment.setPostMessage(postMessage);
        comment.setUsers(usersService.getUserById(userId));
        comment.setTimeStamp(LocalDateTime.now());
        commentService.addNewComment(comment);
        likeService.addCommentLike(comment);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comment")
    public ResponseEntity<?> getAllCommentOfPost(@PathVariable Long postId,
                                                 @PathVariable Long userId, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user", usersService.getUserById(userId));
        return ResponseEntity.ok().body(commentService.getAllCommentById(postId));
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                @PathVariable Long postId,
                                @PathVariable Long userId,
                                @RequestBody Comment comment,
                                HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user", usersService.getUserById(userId));
        Comment existingComment = commentService.getCommentById(commentId);
        existingComment.setCommentBody(comment.getCommentBody());
        existingComment.setCommentId(commentId);
        commentService.updateComment(comment);

        return ResponseEntity.ok().body("Updated");
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<?> removeComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
