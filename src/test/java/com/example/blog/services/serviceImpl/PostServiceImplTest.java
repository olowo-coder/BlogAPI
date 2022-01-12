package com.example.blog.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.blog.model.FavoritePost;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.repository.FavoritePostRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PostServiceImplTest {
    @MockBean
    private FavoritePostRepository favoritePostRepository;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostServiceImpl postServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testGetAllPost() {
        ArrayList<PostMessage> postMessageList = new ArrayList<>();
        when(this.postRepository.findAll()).thenReturn(postMessageList);
        List<PostMessage> actualAllPost = this.postServiceImpl.getAllPost();
        assertSame(postMessageList, actualAllPost);
        assertTrue(actualAllPost.isEmpty());
        verify(this.postRepository).findAll();
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
    }

    @Test
    void testGetAllPost2() {
        when(this.postRepository.findAll()).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.getAllPost());
        verify(this.postRepository).findAll();
    }

    @Test
    void testAddNewPost() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(this.postRepository.save((PostMessage) any())).thenReturn(postMessage);

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
        assertTrue(this.postServiceImpl.addNewPost(postMessage1));
        verify(this.userRepository).findById((Long) any());
        verify(this.postRepository).save((PostMessage) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testAddNewPost2() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.postRepository.save((PostMessage) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.addNewPost(postMessage));
        verify(this.userRepository).findById((Long) any());
        verify(this.postRepository).save((PostMessage) any());
    }

    @Test
    void testAddNewPost3() {
        when(this.userRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        when(this.postRepository.save((PostMessage) any())).thenReturn(postMessage);

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
        assertFalse(this.postServiceImpl.addNewPost(postMessage1));
        verify(this.userRepository).findById((Long) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
    }

    @Test
    void testGetPostById() {
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
        Optional<PostMessage> ofResult = Optional.of(postMessage);
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(postMessage, this.postServiceImpl.getPostById(123L));
        verify(this.postRepository).findById((Long) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testGetPostById2() {
        when(this.postRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.getPostById(123L));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testUpdatePost() {
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
        Optional<PostMessage> ofResult = Optional.of(postMessage);
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);

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
        this.postServiceImpl.updatePost(postMessage1);
        verify(this.postRepository).findById((Long) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testUpdatePost2() {
        when(this.postRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.updatePost(postMessage));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testUpdatePost3() {
        when(this.postRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.updatePost(postMessage));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testDeletePost() {
        doNothing().when(this.postRepository).deleteById((Long) any());
        when(this.postRepository.existsById((Long) any())).thenReturn(true);
        this.postServiceImpl.deletePost(123L);
        verify(this.postRepository).deleteById((Long) any());
        verify(this.postRepository).existsById((Long) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testDeletePost2() {
        doThrow(new IllegalStateException("foo")).when(this.postRepository).deleteById((Long) any());
        when(this.postRepository.existsById((Long) any())).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.deletePost(123L));
        verify(this.postRepository).deleteById((Long) any());
        verify(this.postRepository).existsById((Long) any());
    }

    @Test
    void testDeletePost3() {
        doNothing().when(this.postRepository).deleteById((Long) any());
        when(this.postRepository.existsById((Long) any())).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.deletePost(123L));
        verify(this.postRepository).existsById((Long) any());
    }

    @Test
    void testGetAllPostById() {
        ArrayList<PostMessage> postMessageList = new ArrayList<>();
        when(this.postRepository.findAllByUsersUserId((Long) any())).thenReturn(postMessageList);
        List<PostMessage> actualAllPostById = this.postServiceImpl.getAllPostById(123L);
        assertSame(postMessageList, actualAllPostById);
        assertTrue(actualAllPostById.isEmpty());
        verify(this.postRepository).findAllByUsersUserId((Long) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testGetAllPostById2() {
        when(this.postRepository.findAllByUsersUserId((Long) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.getAllPostById(123L));
        verify(this.postRepository).findAllByUsersUserId((Long) any());
    }

    @Test
    void testGetPostByPostBody() {
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
        when(this.postRepository.getPostMessageByPostBody((String) any())).thenReturn(postMessage);
        assertSame(postMessage, this.postServiceImpl.getPostByPostBody("Not all who wander are lost"));
        verify(this.postRepository).getPostMessageByPostBody((String) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testGetPostByPostBody2() {
        when(this.postRepository.getPostMessageByPostBody((String) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class,
                () -> this.postServiceImpl.getPostByPostBody("Not all who wander are lost"));
        verify(this.postRepository).getPostMessageByPostBody((String) any());
    }

    @Test
    void testFavoritePost() {
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
        Optional<PostMessage> ofResult = Optional.of(postMessage);
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(this.favoritePostRepository.save((FavoritePost) any())).thenReturn(favoritePost);
        this.postServiceImpl.favoritePost(123L);
        verify(this.postRepository).findById((Long) any());
        verify(this.favoritePostRepository).save((FavoritePost) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testFavoritePost2() {
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
        Optional<PostMessage> ofResult = Optional.of(postMessage);
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.favoritePostRepository.save((FavoritePost) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.favoritePost(123L));
        verify(this.postRepository).findById((Long) any());
        verify(this.favoritePostRepository).save((FavoritePost) any());
    }

    @Test
    void testGetAllFavoritePost() {
        ArrayList<FavoritePost> favoritePostList = new ArrayList<>();
        when(this.favoritePostRepository.findAll()).thenReturn(favoritePostList);
        List<FavoritePost> actualAllFavoritePost = this.postServiceImpl.getAllFavoritePost();
        assertSame(favoritePostList, actualAllFavoritePost);
        assertTrue(actualAllFavoritePost.isEmpty());
        verify(this.favoritePostRepository).findAll();
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testGetAllFavoritePost2() {
        when(this.favoritePostRepository.findAll()).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.getAllFavoritePost());
        verify(this.favoritePostRepository).findAll();
    }

    @Test
    void testGetFavoritePostByUser() {
        ArrayList<FavoritePost> favoritePostList = new ArrayList<>();
        when(this.favoritePostRepository.getAllByPostMessageUsersUserId((Long) any())).thenReturn(favoritePostList);
        List<FavoritePost> actualFavoritePostByUser = this.postServiceImpl.getFavoritePostByUser(123L);
        assertSame(favoritePostList, actualFavoritePostByUser);
        assertTrue(actualFavoritePostByUser.isEmpty());
        verify(this.favoritePostRepository).getAllByPostMessageUsersUserId((Long) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testGetFavoritePostByUser2() {
        when(this.favoritePostRepository.getAllByPostMessageUsersUserId((Long) any()))
                .thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.getFavoritePostByUser(123L));
        verify(this.favoritePostRepository).getAllByPostMessageUsersUserId((Long) any());
    }

    @Test
    void testAllPostContains() {
        ArrayList<PostMessage> postMessageList = new ArrayList<>();
        when(this.postRepository.getAllByPostBodyContains((String) any())).thenReturn(postMessageList);
        List<PostMessage> actualAllPostContainsResult = this.postServiceImpl.allPostContains("Post");
        assertSame(postMessageList, actualAllPostContainsResult);
        assertTrue(actualAllPostContainsResult.isEmpty());
        verify(this.postRepository).getAllByPostBodyContains((String) any());
        assertTrue(this.postServiceImpl.getAllFavoritePost().isEmpty());
        assertTrue(this.postServiceImpl.getAllPost().isEmpty());
    }

    @Test
    void testAllPostContains2() {
        when(this.postRepository.getAllByPostBodyContains((String) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.postServiceImpl.allPostContains("Post"));
        verify(this.postRepository).getAllByPostBodyContains((String) any());
    }
}

