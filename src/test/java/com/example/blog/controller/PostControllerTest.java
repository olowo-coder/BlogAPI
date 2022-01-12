package com.example.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.blog.model.FavoritePost;
import com.example.blog.model.LikePost;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PostController.class})
@ExtendWith(SpringExtension.class)
class PostControllerTest {
    @MockBean
    private CommentService commentService;

    @MockBean
    private LikeService likeService;

    @Autowired
    private PostController postController;

    @MockBean
    private PostService postService;

    @MockBean
    private UsersService usersService;

    @Test
    void testAddPost() {
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

        PostMessage postMessage1 = new PostMessage();
        postMessage1.setPostBody("Not all who wander are lost");
        postMessage1.setPostId(123L);
        postMessage1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage1.setUsers(users1);
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.getPostMessageByPostBody((String) any())).thenReturn(postMessage1);
        when(postRepository.save((PostMessage) any())).thenReturn(postMessage);

        Users users2 = new Users();
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhone("4105551212");
        users2.setUserId(123L);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(users2));
        PostServiceImpl postService = new PostServiceImpl(postRepository, userRepository,
                mock(FavoritePostRepository.class));

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
        postMessage2.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage2.setUsers(users3);

        LikePost likePost = new LikePost();
        likePost.setLikePostId(123L);
        likePost.setPLike(1L);
        likePost.setPostMessage(postMessage2);
        LikeRepository likeRepository = mock(LikeRepository.class);
        when(likeRepository.save((LikePost) any())).thenReturn(likePost);
        LikeServiceImpl likePostService = new LikeServiceImpl(likeRepository, mock(LikeCommentRepository.class));

        Users users4 = new Users();
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhone("4105551212");
        users4.setUserId(123L);
        UserRepository userRepository1 = mock(UserRepository.class);
        when(userRepository1.findById((Long) any())).thenReturn(Optional.of(users4));
        UserServiceImpl usersService = new UserServiceImpl(userRepository1, mock(FriendsRepository.class));

        PostController postController = new PostController(postService,
                new CommentServiceImpl(mock(CommentRepository.class)), likePostService, usersService);

        Users users5 = new Users();
        users5.setEmail("jane.doe@example.org");
        users5.setFirstName("Jane");
        users5.setLastName("Doe");
        users5.setPassword("iloveyou");
        users5.setPhone("4105551212");
        users5.setUserId(123L);

        PostMessage postMessage3 = new PostMessage();
        postMessage3.setPostBody("Not all who wander are lost");
        postMessage3.setPostId(123L);
        postMessage3.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage3.setUsers(users5);
        ResponseEntity<?> actualAddPostResult = postController.addPost(postMessage3, 123L, new MockHttpServletRequest());
        assertEquals("User added", actualAddPostResult.getBody());
        assertEquals("<201 CREATED Created,User added,[]>", actualAddPostResult.toString());
        assertEquals(HttpStatus.CREATED, actualAddPostResult.getStatusCode());
        assertTrue(actualAddPostResult.getHeaders().isEmpty());
        verify(postRepository).getPostMessageByPostBody((String) any());
        verify(postRepository).save((PostMessage) any());
        verify(userRepository).findById((Long) any());
        verify(likeRepository).save((LikePost) any());
        verify(userRepository1).findById((Long) any());
        assertEquals(users5, postMessage3.getUsers());
    }

    @Test
    void testUpdatePost() throws Exception {
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
        doNothing().when(this.postService).updatePost((PostMessage) any());
        when(this.postService.getPostById((Long) any())).thenReturn(postMessage);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);

        PostMessage postMessage1 = new PostMessage();
        postMessage1.setPostBody("Not all who wander are lost");
        postMessage1.setPostId(123L);
        postMessage1.setTimeStamp(null);
        postMessage1.setUsers(users1);
        String content = (new ObjectMapper()).writeValueAsString(postMessage1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/blog/user/{userId}/post/{postId}", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Updated"));
    }

    @Test
    void testAddFavoritePost() {
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
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById((Long) any())).thenReturn(Optional.of(postMessage));

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);

        PostMessage postMessage1 = new PostMessage();
        postMessage1.setPostBody("Not all who wander are lost");
        postMessage1.setPostId(123L);
        postMessage1.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage1.setUsers(users1);

        FavoritePost favoritePost = new FavoritePost();
        favoritePost.setFavoritePostId(123L);
        favoritePost.setPostMessage(postMessage1);
        FavoritePostRepository favoritePostRepository = mock(FavoritePostRepository.class);
        when(favoritePostRepository.save((FavoritePost) any())).thenReturn(favoritePost);
        PostServiceImpl postService = new PostServiceImpl(postRepository, mock(UserRepository.class),
                favoritePostRepository);

        CommentServiceImpl commentService = new CommentServiceImpl(mock(CommentRepository.class));
        LikeServiceImpl likePostService = new LikeServiceImpl(mock(LikeRepository.class),
                mock(LikeCommentRepository.class));

        PostController postController = new PostController(postService, commentService, likePostService,
                new UserServiceImpl(mock(UserRepository.class), mock(FriendsRepository.class)));

        Users users2 = new Users();
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhone("4105551212");
        users2.setUserId(123L);

        PostMessage postMessage2 = new PostMessage();
        postMessage2.setPostBody("Not all who wander are lost");
        postMessage2.setPostId(123L);
        postMessage2.setTimeStamp(LocalDateTime.of(1, 1, 1, 1, 1));
        postMessage2.setUsers(users2);
        ResponseEntity<?> actualAddFavoritePostResult = postController.addFavoritePost(postMessage2, 123L, 123L,
                new MockHttpServletRequest());
        assertEquals("Favorite Post added", actualAddFavoritePostResult.getBody());
        assertEquals("<201 CREATED Created,Favorite Post added,[]>", actualAddFavoritePostResult.toString());
        assertEquals(HttpStatus.CREATED, actualAddFavoritePostResult.getStatusCode());
        assertTrue(actualAddFavoritePostResult.getHeaders().isEmpty());
        verify(postRepository).findById((Long) any());
        verify(favoritePostRepository).save((FavoritePost) any());
    }

    @Test
    void testGetAllUserPost() throws Exception {
        when(this.postService.getAllPostById((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/user/{userId}/post", 123L);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllUserPost2() throws Exception {
        when(this.postService.getAllPostById((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/user/{userId}/post", 123L);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetUserFavoritePost() throws Exception {
        when(this.postService.getFavoritePostByUser((Long) any())).thenReturn(new ArrayList<>());

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
        postMessage.setTimeStamp(null);
        postMessage.setUsers(users);
        String content = (new ObjectMapper()).writeValueAsString(postMessage);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/user/{userId}/post/favorite", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string(" added"));
    }

    @Test
    void testRemoveComment() throws Exception {
        doNothing().when(this.postService).deletePost((Long) any());
        doNothing().when(this.likeService).deleteLikePost((Long) any());
        doNothing().when(this.commentService).deleteAllByPostId((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/blog/user/{userId}/post/{postId}",
                123L, 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testRemoveComment2() throws Exception {
        doNothing().when(this.postService).deletePost((Long) any());
        doNothing().when(this.likeService).deleteLikePost((Long) any());
        doNothing().when(this.commentService).deleteAllByPostId((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/blog/user/{userId}/post/{postId}",
                123L, 123L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

