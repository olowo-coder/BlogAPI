package com.example.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private CommentService commentService;

    @MockBean
    private LikeService likeService;

    @MockBean
    private PostService postService;

    @Autowired
    private UserController userController;

    @MockBean
    private UsersService usersService;

    @Test
    void testGetAllPost() throws Exception {
        when(this.postService.getAllPost()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/post/all");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllPost2() throws Exception {
        when(this.postService.getAllPost()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/post/all");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAddUsers() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(users));
        UserServiceImpl userService = new UserServiceImpl(userRepository, mock(FriendsRepository.class));

        PostServiceImpl postService = new PostServiceImpl(mock(PostRepository.class), mock(UserRepository.class),
                mock(FavoritePostRepository.class));

        CommentServiceImpl commentService = new CommentServiceImpl(mock(CommentRepository.class));
        UserController userController = new UserController(userService, postService, commentService,
                new LikeServiceImpl(mock(LikeRepository.class), mock(LikeCommentRepository.class)));

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);
        ResponseEntity<?> actualAddUsersResult = userController.addUsers(users1, new MockHttpServletRequest());
        assertEquals("Email Taken", actualAddUsersResult.getBody());
        assertEquals("<200 OK OK,Email Taken,[]>", actualAddUsersResult.toString());
        assertEquals(HttpStatus.OK, actualAddUsersResult.getStatusCode());
        assertTrue(actualAddUsersResult.getHeaders().isEmpty());
        verify(userRepository).findByEmail((String) any());
    }

    @Test
    void testAddUsers2() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((Users) any())).thenReturn(users);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        UserServiceImpl userService = new UserServiceImpl(userRepository, mock(FriendsRepository.class));

        PostServiceImpl postService = new PostServiceImpl(mock(PostRepository.class), mock(UserRepository.class),
                mock(FavoritePostRepository.class));

        CommentServiceImpl commentService = new CommentServiceImpl(mock(CommentRepository.class));
        UserController userController = new UserController(userService, postService, commentService,
                new LikeServiceImpl(mock(LikeRepository.class), mock(LikeCommentRepository.class)));

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);
        ResponseEntity<?> actualAddUsersResult = userController.addUsers(users1, new MockHttpServletRequest());
        assertEquals("User added", actualAddUsersResult.getBody());
        assertEquals("<201 CREATED Created,User added,[]>", actualAddUsersResult.toString());
        assertEquals(HttpStatus.CREATED, actualAddUsersResult.getStatusCode());
        assertTrue(actualAddUsersResult.getHeaders().isEmpty());
        verify(userRepository).findByEmail((String) any());
        verify(userRepository).save((Users) any());
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(this.usersService).deleteUser((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/blog/user/{userId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteUser2() throws Exception {
        doNothing().when(this.usersService).deleteUser((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/blog/user/{userId}", 123L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testAllComment() throws Exception {
        when(this.commentService.getAllComment()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/comment/all");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAllComment2() throws Exception {
        when(this.commentService.getAllComment()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/comment/all");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAllFavorite() throws Exception {
        when(this.postService.getAllFavoritePost()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/favorite/all");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAllFavorite2() throws Exception {
        when(this.postService.getAllFavoritePost()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/favorite/all");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAllOfUsers() throws Exception {
        when(this.usersService.getAllFriendByUserId((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/user/{userId}/connect", 123L);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAllOfUsers2() throws Exception {
        when(this.usersService.getAllFriendByUserId((Long) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/user/{userId}/connect", 123L);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAllUsers() throws Exception {
        when(this.usersService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/all");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAllUsers2() throws Exception {
        when(this.usersService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/all");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllLikePost() throws Exception {
        when(this.likeService.getAllLikesOfPost()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/likePost/all");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllLikePost2() throws Exception {
        when(this.likeService.getAllLikesOfPost()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/likePost/all");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testHomepage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Welcome to Blogger"));
    }

    @Test
    void testHomepage2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Welcome to Blogger"));
    }

    @Test
    void testIndex() throws Exception {
        when(this.usersService.validateUser((Users) any())).thenReturn("2020-03-01");
        when(this.postService.getAllPost()).thenReturn(new ArrayList<>());

        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        String content = (new ObjectMapper()).writeValueAsString(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/blog/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testIndex2() throws Exception {
        when(this.usersService.validateUser((Users) any())).thenReturn("Not Registered Email");
        when(this.postService.getAllPost()).thenReturn(new ArrayList<>());

        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        String content = (new ObjectMapper()).writeValueAsString(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/blog/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Not Registered Email"));
    }

    @Test
    void testIndex3() throws Exception {
        when(this.usersService.validateUser((Users) any())).thenReturn("Incorrect Password");
        when(this.postService.getAllPost()).thenReturn(new ArrayList<>());

        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        String content = (new ObjectMapper()).writeValueAsString(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/blog/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Incorrect Password"));
    }

    @Test
    void testLogOut() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/log-out");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(301));
    }

    @Test
    void testLogOut2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/blog/log-out");
        getResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController).build().perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(301));
    }

    @Test
    void testMakeFriends() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/blog/user/{userId}/connect/{friendId}",
                123L, 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Cannot connect to yourself"));
    }

    @Test
    void testMakeFriends2() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/blog/user/{userId}/connect/{friendId}",
                123L, 123L);
        postResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(postResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Cannot connect to yourself"));
    }

    @Test
    void testSearchingComment() throws Exception {
        when(this.commentService.allCommentContains((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/searching/comment")
                .param("comment", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testSearchingPost() throws Exception {
        when(this.postService.allPostContains((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/blog/searching/post")
                .param("post", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

