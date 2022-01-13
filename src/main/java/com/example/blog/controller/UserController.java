package com.example.blog.controller;


import com.example.blog.model.Comment;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.services.CommentService;
import com.example.blog.services.LikeService;
import com.example.blog.services.PostService;
import com.example.blog.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/blog")
@Slf4j
public class UserController {

    private final UsersService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

    @Autowired
    public UserController(UsersService userService, PostService postService, CommentService commentService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @GetMapping
    public String homepage(){
        return "Welcome to Blogger";
    }

    @GetMapping("/all")
    public ResponseEntity<?> allUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/all/page")
    public ResponseEntity<?> pageAllUsers(@RequestParam(value = "by") String by,
                                          @RequestParam(value = "next", defaultValue = "2") int next){
        return ResponseEntity.ok().body(userService.pageGetAllUsers(by, next));
    }

    @GetMapping("/post/all")
    public ResponseEntity<?> getAllPost(){
        return ResponseEntity.ok().body(postService.getAllPost());
    }

    @GetMapping("post/all/page")
    public ResponseEntity<?> pageAllPosts(@RequestParam(value = "by") String by,
                                          @RequestParam(value = "next", defaultValue = "2") int next){
        return ResponseEntity.ok().body(postService.pageGetAllPosts(by, next));
    }

    @GetMapping("/comment/all")
    public ResponseEntity<?> allComment(HttpServletRequest request){
        HttpSession session = request.getSession();
        return ResponseEntity.ok().body(commentService.getAllComment());
    }

    @GetMapping("/favorite/all")
    public ResponseEntity<?> allFavorite(HttpServletRequest request){
        HttpSession session = request.getSession();
        return ResponseEntity.ok().body(postService.getAllFavoritePost());
    }

    @GetMapping("/likePost/all")
    public ResponseEntity<?> getAllLikePost(){
        return new ResponseEntity<>(likeService.getAllLikesOfPost(), HttpStatus.OK);
    }

    @GetMapping("/searching/post")
    public ResponseEntity<?> searchingPost(@RequestParam(value = "post") String post){
        return new ResponseEntity<>(postService.allPostContains(post), HttpStatus.OK);
    }

    @GetMapping("/searching/comment")
    public ResponseEntity<?> searchingComment(@RequestParam(value = "comment") String comment){
        return new ResponseEntity<>(commentService.allCommentContains(comment), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUsers(@RequestBody Users user, HttpServletRequest request){
        HttpSession session = request.getSession();
        boolean check = userService.addUser(user);
        if(check){
            session.setAttribute("error", "Email Taken");
            return new ResponseEntity<>("Email Taken", HttpStatus.OK);
        }
        session.setAttribute("user", user);
        return new ResponseEntity<>("User added", HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> index(@RequestBody Users user, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String check = userService.validateUser(user);
        if (check.equals("Not Registered Email")) {
            return ResponseEntity.ok().body("Not Registered Email");
        }


        if (check.equals("Incorrect Password")) {
            return ResponseEntity.ok().body("Incorrect Password");
        }

        session.setAttribute("user", user.getEmail());
        List<PostMessage> allPost = postService.getAllPost();
        return ResponseEntity.ok().body(allPost);
    }


    @GetMapping("/log-out")
    public ResponseEntity<?> logOut( HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, "").build();
    }

    @DeleteMapping("user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/{userId}/connect/{friendId}")
    public ResponseEntity<?> makeFriends(@PathVariable Long userId, @PathVariable Long friendId){
        if(userId.equals(friendId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot connect to yourself");
        }
        userService.connectFriends(userId, friendId);
        return ResponseEntity.ok().body("Connected");
    }

    @GetMapping("/user/{userId}/connect")
    public ResponseEntity<?> allOfUsers(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getAllFriendByUserId(userId), HttpStatus.OK);
    }

}
