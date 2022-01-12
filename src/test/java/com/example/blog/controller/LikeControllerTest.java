package com.example.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.blog.model.Comment;
import com.example.blog.model.LikeComment;
import com.example.blog.model.LikePost;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.repository.FavoritePostRepository;
import com.example.blog.repository.FriendsRepository;
import com.example.blog.repository.LikeCommentRepository;
import com.example.blog.repository.LikeRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.services.LikeService;
import com.example.blog.services.PostService;
import com.example.blog.services.UsersService;
import com.example.blog.services.serviceImpl.LikeServiceImpl;
import com.example.blog.services.serviceImpl.PostServiceImpl;
import com.example.blog.services.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LikeController.class})
@ExtendWith(SpringExtension.class)
class LikeControllerTest {
    @Autowired
    private LikeController likeController;

    @MockBean
    private LikeService likeService;

    @MockBean
    private PostService postService;

    @MockBean
    private UsersService usersService;

    @Test
    void testUpdateLikePost() throws Exception {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        when(this.usersService.getUserById((Long) any())).thenReturn(users);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);

        PostMessage postMessage = new PostMessage();
        postMessage.setPostBody("Not all who wander are lost");
        postMessage.setPostId(123L);
        postMessage.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage.setUsers(users1);
        when(this.postService.getPostById((Long) any())).thenReturn(postMessage);

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

        LikePost likePost = new LikePost();
        likePost.setLikePostId(123L);
        likePost.setPLike(1L);
        likePost.setPostMessage(postMessage1);
        doNothing().when(this.likeService).updateLikePost((LikePost) any());
        when(this.likeService.checkIfExists((Long) any())).thenReturn(likePost);

        Users users3 = new Users();
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhone("4105551212");
        users3.setUserId(123L);

        PostMessage postMessage2 = new PostMessage();
        postMessage2.setPostBody("Not all who wander are lost");
        postMessage2.setPostId(123L);
        postMessage2.setTimeStamp(null);
        postMessage2.setUsers(users3);

        LikePost likePost1 = new LikePost();
        likePost1.setLikePostId(123L);
        likePost1.setPLike(1L);
        likePost1.setPostMessage(postMessage2);
        String content = (new ObjectMapper()).writeValueAsString(likePost1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/blog/user/{userId}/post/{postId}/like", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.likeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Updated"));
    }

    @Test
    void testUpdateLikePost2() throws Exception {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        when(this.usersService.getUserById((Long) any())).thenReturn(users);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);

        PostMessage postMessage = new PostMessage();
        postMessage.setPostBody("Not all who wander are lost");
        postMessage.setPostId(123L);
        postMessage.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage.setUsers(users1);
        when(this.postService.getPostById((Long) any())).thenReturn(postMessage);

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

        LikePost likePost = new LikePost();
        likePost.setLikePostId(123L);
        likePost.setPLike(0L);
        likePost.setPostMessage(postMessage1);
        doNothing().when(this.likeService).updateLikePost((LikePost) any());
        when(this.likeService.checkIfExists((Long) any())).thenReturn(likePost);

        Users users3 = new Users();
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhone("4105551212");
        users3.setUserId(123L);

        PostMessage postMessage2 = new PostMessage();
        postMessage2.setPostBody("Not all who wander are lost");
        postMessage2.setPostId(123L);
        postMessage2.setTimeStamp(null);
        postMessage2.setUsers(users3);

        LikePost likePost1 = new LikePost();
        likePost1.setLikePostId(123L);
        likePost1.setPLike(1L);
        likePost1.setPostMessage(postMessage2);
        String content = (new ObjectMapper()).writeValueAsString(likePost1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/blog/user/{userId}/post/{postId}/like", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.likeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Updated"));
    }

    @Test
    void testGetLikesOfComment() throws Exception {
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

        LikeComment likeComment = new LikeComment();
        likeComment.setCLike(1L);
        likeComment.setComment(comment);
        likeComment.setLikeCommentId(123L);
        when(this.likeService.getCommentLikeById((Long) any())).thenReturn(likeComment);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/blog/user/{userId}/comment/{commentId}/like", 123L, 123L);
        MockMvcBuilders.standaloneSetup(this.likeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"likeCommentId\":123,\"comment\":{\"commentId\":123,\"commentBody\":\"Not all who wander are lost\",\"timeStamp"
                                        + "\":[1,1,1,1,1],\"users\":{\"userId\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org"
                                        + "\",\"phone\":\"4105551212\",\"password\":\"iloveyou\"},\"postMessage\":{\"postId\":123,\"postBody\":\"Not all who"
                                        + " wander are lost\",\"users\":{\"userId\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example"
                                        + ".org\",\"phone\":\"4105551212\",\"password\":\"iloveyou\"},\"timeStamp\":[1,1,1,1,1]}},\"clike\":1}"));
    }

    @Test
    void testUpdateLikeComment() {
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

        LikeComment likeComment = new LikeComment();
        likeComment.setCLike(1L);
        likeComment.setComment(comment);
        likeComment.setLikeCommentId(123L);

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

        Users users3 = new Users();
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhone("4105551212");
        users3.setUserId(123L);

        Comment comment1 = new Comment();
        comment1.setCommentBody("Not all who wander are lost");
        comment1.setCommentId(123L);
        comment1.setPostMessage(postMessage1);
        comment1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment1.setUsers(users3);

        LikeComment likeComment1 = new LikeComment();
        likeComment1.setCLike(1L);
        likeComment1.setComment(comment1);
        likeComment1.setLikeCommentId(123L);
        LikeCommentRepository likeCommentRepository = mock(LikeCommentRepository.class);
        when(likeCommentRepository.findById((Long) any())).thenReturn(Optional.of(likeComment1));
        when(likeCommentRepository.getLikeCommentByComment_CommentId((Long) any())).thenReturn(likeComment);
        LikeServiceImpl likeService = new LikeServiceImpl(mock(LikeRepository.class), likeCommentRepository);

        PostServiceImpl postService = new PostServiceImpl(mock(PostRepository.class), mock(UserRepository.class),
                mock(FavoritePostRepository.class));

        LikeController likeController = new LikeController(likeService, postService,
                new UserServiceImpl(mock(UserRepository.class), mock(FriendsRepository.class)));

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

        Comment comment2 = new Comment();
        comment2.setCommentBody("Not all who wander are lost");
        comment2.setCommentId(123L);
        comment2.setPostMessage(postMessage2);
        comment2.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment2.setUsers(users5);

        LikeComment likeComment2 = new LikeComment();
        likeComment2.setCLike(1L);
        likeComment2.setComment(comment2);
        likeComment2.setLikeCommentId(123L);
        ResponseEntity<?> actualUpdateLikeCommentResult = likeController.updateLikeComment(likeComment2, 123L, 123L,
                new MockHttpServletRequest());
        assertEquals("Updated", actualUpdateLikeCommentResult.getBody());
        assertEquals("<200 OK OK,Updated,[]>", actualUpdateLikeCommentResult.toString());
        assertEquals(HttpStatus.OK, actualUpdateLikeCommentResult.getStatusCode());
        assertTrue(actualUpdateLikeCommentResult.getHeaders().isEmpty());
        verify(likeCommentRepository).findById((Long) any());
        verify(likeCommentRepository).getLikeCommentByComment_CommentId((Long) any());
    }

    @Test
    void testUpdateLikeComment2() {
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

        LikeComment likeComment = new LikeComment();
        likeComment.setCLike(0L);
        likeComment.setComment(comment);
        likeComment.setLikeCommentId(123L);

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

        Users users3 = new Users();
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhone("4105551212");
        users3.setUserId(123L);

        Comment comment1 = new Comment();
        comment1.setCommentBody("Not all who wander are lost");
        comment1.setCommentId(123L);
        comment1.setPostMessage(postMessage1);
        comment1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment1.setUsers(users3);

        LikeComment likeComment1 = new LikeComment();
        likeComment1.setCLike(1L);
        likeComment1.setComment(comment1);
        likeComment1.setLikeCommentId(123L);
        LikeCommentRepository likeCommentRepository = mock(LikeCommentRepository.class);
        when(likeCommentRepository.findById((Long) any())).thenReturn(Optional.of(likeComment1));
        when(likeCommentRepository.getLikeCommentByComment_CommentId((Long) any())).thenReturn(likeComment);
        LikeServiceImpl likeService = new LikeServiceImpl(mock(LikeRepository.class), likeCommentRepository);

        PostServiceImpl postService = new PostServiceImpl(mock(PostRepository.class), mock(UserRepository.class),
                mock(FavoritePostRepository.class));

        LikeController likeController = new LikeController(likeService, postService,
                new UserServiceImpl(mock(UserRepository.class), mock(FriendsRepository.class)));

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

        Comment comment2 = new Comment();
        comment2.setCommentBody("Not all who wander are lost");
        comment2.setCommentId(123L);
        comment2.setPostMessage(postMessage2);
        comment2.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        comment2.setUsers(users5);

        LikeComment likeComment2 = new LikeComment();
        likeComment2.setCLike(1L);
        likeComment2.setComment(comment2);
        likeComment2.setLikeCommentId(123L);
        ResponseEntity<?> actualUpdateLikeCommentResult = likeController.updateLikeComment(likeComment2, 123L, 123L,
                new MockHttpServletRequest());
        assertEquals("Updated", actualUpdateLikeCommentResult.getBody());
        assertEquals("<200 OK OK,Updated,[]>", actualUpdateLikeCommentResult.toString());
        assertEquals(HttpStatus.OK, actualUpdateLikeCommentResult.getStatusCode());
        assertTrue(actualUpdateLikeCommentResult.getHeaders().isEmpty());
        verify(likeCommentRepository).findById((Long) any());
        verify(likeCommentRepository).getLikeCommentByComment_CommentId((Long) any());
    }

    @Test
    void testGetLikesOfPost() throws Exception {
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

        LikePost likePost = new LikePost();
        likePost.setLikePostId(123L);
        likePost.setPLike(1L);
        likePost.setPostMessage(postMessage);
        when(this.likeService.checkIfExists((Long) any())).thenReturn(likePost);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/user/{userId}/post/{postId}/like",
                123L, 123L);
        MockMvcBuilders.standaloneSetup(this.likeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"likePostId\":123,\"postMessage\":{\"postId\":123,\"postBody\":\"Not all who wander are lost\",\"users\":{\"userId"
                                        + "\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"phone\":\"4105551212\",\"password"
                                        + "\":\"iloveyou\"},\"timeStamp\":[1,1,1,1,1]},\"plike\":1}"));
    }
}

