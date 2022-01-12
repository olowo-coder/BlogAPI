package com.example.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.blog.model.Comment;
import com.example.blog.model.LikeComment;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.FavoritePostRepository;
import com.example.blog.repository.FriendsRepository;
import com.example.blog.repository.LikeCommentRepository;
import com.example.blog.repository.LikeRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.services.CommentService;
import com.example.blog.services.LikeService;
import com.example.blog.services.PostService;
import com.example.blog.services.UsersService;
import com.example.blog.services.serviceImpl.CommentServiceImpl;
import com.example.blog.services.serviceImpl.LikeServiceImpl;
import com.example.blog.services.serviceImpl.PostServiceImpl;
import com.example.blog.services.serviceImpl.UserServiceImpl;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CommentController.class})
@ExtendWith(SpringExtension.class)
class CommentControllerTest {
    @Autowired
    private CommentController commentController;

    @MockBean
    private CommentService commentService;

    @MockBean
    private LikeService likeService;

    @MockBean
    private PostService postService;

    @MockBean
    private UsersService usersService;

    @Test
    void testSaveComment() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);

        PostMessage postMessage = new PostMessage();
        postMessage.setPostBody("Not all who wander are lost");
        postMessage.setPostId(123L);
        postMessage.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage.setUsers(users);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);

        Comment comment = new Comment();
        comment.setCommentBody("Not all who wander are lost");
        comment.setCommentId(123L);
        comment.setPostMessage(postMessage);
        comment.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setUsers(users1);
        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.save((Comment) any())).thenReturn(comment);
        CommentServiceImpl commentService = new CommentServiceImpl(commentRepository);

        Users users2 = new Users();
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhone("4105551212");
        users2.setUserId(123L);

        PostMessage postMessage1 = new PostMessage();
        postMessage1.setPostBody("Not all who wander are lost");
        postMessage1.setPostId(123L);
        postMessage1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage1.setUsers(users2);
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById((Long) any())).thenReturn(Optional.of(postMessage1));
        PostServiceImpl postService = new PostServiceImpl(postRepository, mock(UserRepository.class),
                mock(FavoritePostRepository.class));

        Users users3 = new Users();
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhone("4105551212");
        users3.setUserId(123L);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(users3));
        UserServiceImpl usersService = new UserServiceImpl(userRepository, mock(FriendsRepository.class));

        Users users4 = new Users();
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhone("4105551212");
        users4.setUserId(123L);

        PostMessage postMessage2 = new PostMessage();
        postMessage2.setPostBody("Not all who wander are lost");
        postMessage2.setPostId(123L);
        postMessage2.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage2.setUsers(users4);

        Users users5 = new Users();
        users5.setEmail("jane.doe@example.org");
        users5.setFirstName("Jane");
        users5.setLastName("Doe");
        users5.setPassword("iloveyou");
        users5.setPhone("4105551212");
        users5.setUserId(123L);

        Comment comment1 = new Comment();
        comment1.setCommentBody("Not all who wander are lost");
        comment1.setCommentId(123L);
        comment1.setPostMessage(postMessage2);
        comment1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment1.setUsers(users5);

        LikeComment likeComment = new LikeComment();
        likeComment.setCLike(1L);
        likeComment.setComment(comment1);
        likeComment.setLikeCommentId(123L);
        LikeCommentRepository likeCommentRepository = mock(LikeCommentRepository.class);
        when(likeCommentRepository.save((LikeComment) any())).thenReturn(likeComment);
        CommentController commentController = new CommentController(commentService, postService, usersService,
                new LikeServiceImpl(mock(LikeRepository.class), likeCommentRepository));

        Users users6 = new Users();
        users6.setEmail("jane.doe@example.org");
        users6.setFirstName("Jane");
        users6.setLastName("Doe");
        users6.setPassword("iloveyou");
        users6.setPhone("4105551212");
        users6.setUserId(123L);

        PostMessage postMessage3 = new PostMessage();
        postMessage3.setPostBody("Not all who wander are lost");
        postMessage3.setPostId(123L);
        postMessage3.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage3.setUsers(users6);

        Users users7 = new Users();
        users7.setEmail("jane.doe@example.org");
        users7.setFirstName("Jane");
        users7.setLastName("Doe");
        users7.setPassword("iloveyou");
        users7.setPhone("4105551212");
        users7.setUserId(123L);

        Comment comment2 = new Comment();
        comment2.setCommentBody("Not all who wander are lost");
        comment2.setCommentId(123L);
        comment2.setPostMessage(postMessage3);
        comment2.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment2.setUsers(users7);
        ResponseEntity<?> actualSaveCommentResult = commentController.saveComment(comment2, 123L, 123L,
                new MockHttpServletRequest());
        assertEquals("Created", actualSaveCommentResult.getBody());
        assertEquals("<201 CREATED Created,Created,[]>", actualSaveCommentResult.toString());
        assertEquals(HttpStatus.CREATED, actualSaveCommentResult.getStatusCode());
        assertTrue(actualSaveCommentResult.getHeaders().isEmpty());
        verify(commentRepository).save((Comment) any());
        verify(postRepository).findById((Long) any());
        verify(userRepository, atLeast(1)).findById((Long) any());
        verify(likeCommentRepository).save((LikeComment) any());
        assertEquals(users6, comment2.getUsers());
        assertEquals(postMessage3, comment2.getPostMessage());
    }

    @Test
    void testGetAllCommentOfPost() {
        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.findByPostMessagePostId((Long) any())).thenReturn(new ArrayList<>());
        CommentServiceImpl commentService = new CommentServiceImpl(commentRepository);

        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(users));
        UserServiceImpl usersService = new UserServiceImpl(userRepository, mock(FriendsRepository.class));

        PostServiceImpl postService = new PostServiceImpl(mock(PostRepository.class), mock(UserRepository.class),
                mock(FavoritePostRepository.class));

        CommentController commentController = new CommentController(commentService, postService, usersService,
                new LikeServiceImpl(mock(LikeRepository.class), mock(LikeCommentRepository.class)));
        ResponseEntity<?> actualAllCommentOfPost = commentController.getAllCommentOfPost(123L, 123L,
                new MockHttpServletRequest());
        assertEquals("<200 OK OK,[],[]>", actualAllCommentOfPost.toString());
        assertTrue(actualAllCommentOfPost.hasBody());
        assertEquals(HttpStatus.OK, actualAllCommentOfPost.getStatusCode());
        assertTrue(actualAllCommentOfPost.getHeaders().isEmpty());
        verify(commentRepository).findByPostMessagePostId((Long) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testUpdateComment() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);

        PostMessage postMessage = new PostMessage();
        postMessage.setPostBody("Not all who wander are lost");
        postMessage.setPostId(123L);
        postMessage.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage.setUsers(users);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);

        Comment comment = new Comment();
        comment.setCommentBody("Not all who wander are lost");
        comment.setCommentId(123L);
        comment.setPostMessage(postMessage);
        comment.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment.setUsers(users1);
        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.findById((Long) any())).thenReturn(Optional.of(comment));
        CommentServiceImpl commentService = new CommentServiceImpl(commentRepository);

        Users users2 = new Users();
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhone("4105551212");
        users2.setUserId(123L);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(users2));
        UserServiceImpl usersService = new UserServiceImpl(userRepository, mock(FriendsRepository.class));

        PostServiceImpl postService = new PostServiceImpl(mock(PostRepository.class), mock(UserRepository.class),
                mock(FavoritePostRepository.class));

        CommentController commentController = new CommentController(commentService, postService, usersService,
                new LikeServiceImpl(mock(LikeRepository.class), mock(LikeCommentRepository.class)));

        Users users3 = new Users();
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhone("4105551212");
        users3.setUserId(123L);

        PostMessage postMessage1 = new PostMessage();
        postMessage1.setPostBody("Not all who wander are lost");
        postMessage1.setPostId(123L);
        postMessage1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage1.setUsers(users3);

        Users users4 = new Users();
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhone("4105551212");
        users4.setUserId(123L);

        Comment comment1 = new Comment();
        comment1.setCommentBody("Not all who wander are lost");
        comment1.setCommentId(123L);
        comment1.setPostMessage(postMessage1);
        comment1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment1.setUsers(users4);
        ResponseEntity<?> actualUpdateCommentResult = commentController.updateComment(123L, 123L, 123L, comment1,
                new MockHttpServletRequest());
        assertEquals("Updated", actualUpdateCommentResult.getBody());
        assertEquals("<200 OK OK,Updated,[]>", actualUpdateCommentResult.toString());
        assertEquals(HttpStatus.OK, actualUpdateCommentResult.getStatusCode());
        assertTrue(actualUpdateCommentResult.getHeaders().isEmpty());
        verify(commentRepository, atLeast(1)).findById((Long) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testRemoveComment() throws Exception {
        doNothing().when(this.commentService).deleteComment((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/blog/user/{userId}/post/{postId}/comment/{commentId}", 123L, 123L, 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testRemoveComment2() throws Exception {
        doNothing().when(this.commentService).deleteComment((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
                .delete("/blog/user/{userId}/post/{postId}/comment/{commentId}", 123L, 123L, 123L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.commentController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

