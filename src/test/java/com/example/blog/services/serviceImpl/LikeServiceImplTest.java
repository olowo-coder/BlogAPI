package com.example.blog.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.blog.model.Comment;
import com.example.blog.model.LikeComment;
import com.example.blog.model.LikePost;
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.repository.LikeCommentRepository;
import com.example.blog.repository.LikeRepository;

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

@ContextConfiguration(classes = {LikeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LikeServiceImplTest {
    @MockBean
    private LikeCommentRepository likeCommentRepository;

    @MockBean
    private LikeRepository likeRepository;

    @Autowired
    private LikeServiceImpl likeServiceImpl;

    @Test
    void testGetAllLikesOfPost() {
        ArrayList<LikePost> likePostList = new ArrayList<>();
        when(this.likeRepository.findAll()).thenReturn(likePostList);
        List<LikePost> actualAllLikesOfPost = this.likeServiceImpl.getAllLikesOfPost();
        assertSame(likePostList, actualAllLikesOfPost);
        assertTrue(actualAllLikesOfPost.isEmpty());
        verify(this.likeRepository).findAll();
    }

    @Test
    void testGetAllLikesOfPost2() {
        when(this.likeRepository.findAll()).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.getAllLikesOfPost());
        verify(this.likeRepository).findAll();
    }

    @Test
    void testGetAllLikesOfPost3() {
        ArrayList<LikePost> likePostList = new ArrayList<>();
        when(this.likeRepository.findAll()).thenReturn(likePostList);
        List<LikePost> actualAllLikesOfPost = this.likeServiceImpl.getAllLikesOfPost();
        assertSame(likePostList, actualAllLikesOfPost);
        assertTrue(actualAllLikesOfPost.isEmpty());
        verify(this.likeRepository).findAll();
    }

    @Test
    void testGetAllLikesOfPost4() {
        when(this.likeRepository.findAll()).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.getAllLikesOfPost());
        verify(this.likeRepository).findAll();
    }

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

        LikePost likePost = new LikePost();
        likePost.setLikePostId(123L);
        likePost.setPLike(1L);
        likePost.setPostMessage(postMessage);
        when(this.likeRepository.save((LikePost) any())).thenReturn(likePost);

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
        this.likeServiceImpl.addPost(postMessage1);
        verify(this.likeRepository).save((LikePost) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testAddPost2() {
        when(this.likeRepository.save((LikePost) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.addPost(postMessage));
        verify(this.likeRepository).save((LikePost) any());
    }

    @Test
    void testAddPost3() {
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
        when(this.likeRepository.save((LikePost) any())).thenReturn(likePost);

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
        this.likeServiceImpl.addPost(postMessage1);
        verify(this.likeRepository).save((LikePost) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testAddPost4() {
        when(this.likeRepository.save((LikePost) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.addPost(postMessage));
        verify(this.likeRepository).save((LikePost) any());
    }

    @Test
    void testGetCommentLikeById() {
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
        when(this.likeCommentRepository.getLikeCommentByComment_CommentId((Long) any())).thenReturn(likeComment);
        assertSame(likeComment, this.likeServiceImpl.getCommentLikeById(123L));
        verify(this.likeCommentRepository).getLikeCommentByComment_CommentId((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testGetCommentLikeById2() {
        when(this.likeCommentRepository.getLikeCommentByComment_CommentId((Long) any()))
                .thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.getCommentLikeById(123L));
        verify(this.likeCommentRepository).getLikeCommentByComment_CommentId((Long) any());
    }

    @Test
    void testGetCommentLikeById3() {
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
        when(this.likeCommentRepository.getLikeCommentByComment_CommentId((Long) any())).thenReturn(likeComment);
        assertSame(likeComment, this.likeServiceImpl.getCommentLikeById(123L));
        verify(this.likeCommentRepository).getLikeCommentByComment_CommentId((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testGetCommentLikeById4() {
        when(this.likeCommentRepository.getLikeCommentByComment_CommentId((Long) any()))
                .thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.getCommentLikeById(123L));
        verify(this.likeCommentRepository).getLikeCommentByComment_CommentId((Long) any());
    }

    @Test
    void testAddCommentLike() {
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
        when(this.likeCommentRepository.save((LikeComment) any())).thenReturn(likeComment);

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
        this.likeServiceImpl.addCommentLike(comment1);
        verify(this.likeCommentRepository).save((LikeComment) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testAddCommentLike2() {
        when(this.likeCommentRepository.save((LikeComment) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.addCommentLike(comment));
        verify(this.likeCommentRepository).save((LikeComment) any());
    }

    @Test
    void testAddCommentLike3() {
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
        when(this.likeCommentRepository.save((LikeComment) any())).thenReturn(likeComment);

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
        this.likeServiceImpl.addCommentLike(comment1);
        verify(this.likeCommentRepository).save((LikeComment) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testAddCommentLike4() {
        when(this.likeCommentRepository.save((LikeComment) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.addCommentLike(comment));
        verify(this.likeCommentRepository).save((LikeComment) any());
    }

    @Test
    void testCheckIfExists() {
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
        when(this.likeRepository.getLikePostByPostMessagePostId((Long) any())).thenReturn(likePost);
        assertSame(likePost, this.likeServiceImpl.checkIfExists(123L));
        verify(this.likeRepository).getLikePostByPostMessagePostId((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testCheckIfExists2() {
        when(this.likeRepository.getLikePostByPostMessagePostId((Long) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.checkIfExists(123L));
        verify(this.likeRepository).getLikePostByPostMessagePostId((Long) any());
    }

    @Test
    void testCheckIfExists3() {
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
        when(this.likeRepository.getLikePostByPostMessagePostId((Long) any())).thenReturn(likePost);
        assertSame(likePost, this.likeServiceImpl.checkIfExists(123L));
        verify(this.likeRepository).getLikePostByPostMessagePostId((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testCheckIfExists4() {
        when(this.likeRepository.getLikePostByPostMessagePostId((Long) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.checkIfExists(123L));
        verify(this.likeRepository).getLikePostByPostMessagePostId((Long) any());
    }

    @Test
    void testAddLikePost() {
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
        when(this.likeRepository.save((LikePost) any())).thenReturn(likePost);

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

        LikePost likePost1 = new LikePost();
        likePost1.setLikePostId(123L);
        likePost1.setPLike(1L);
        likePost1.setPostMessage(postMessage1);
        assertTrue(this.likeServiceImpl.addLikePost(likePost1));
        verify(this.likeRepository).save((LikePost) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testAddLikePost2() {
        when(this.likeRepository.save((LikePost) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.addLikePost(likePost));
        verify(this.likeRepository).save((LikePost) any());
    }

    @Test
    void testAddLikePost3() {
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
        when(this.likeRepository.save((LikePost) any())).thenReturn(likePost);

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

        LikePost likePost1 = new LikePost();
        likePost1.setLikePostId(123L);
        likePost1.setPLike(1L);
        likePost1.setPostMessage(postMessage1);
        assertTrue(this.likeServiceImpl.addLikePost(likePost1));
        verify(this.likeRepository).save((LikePost) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testAddLikePost4() {
        when(this.likeRepository.save((LikePost) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.addLikePost(likePost));
        verify(this.likeRepository).save((LikePost) any());
    }

    @Test
    void testDeleteLikePost() {
        doNothing().when(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
        this.likeServiceImpl.deleteLikePost(123L);
        verify(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testDeleteLikePost2() {
        doThrow(new IllegalStateException("foo")).when(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.deleteLikePost(123L));
        verify(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
    }

    @Test
    void testDeleteLikePost3() {
        doNothing().when(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
        this.likeServiceImpl.deleteLikePost(123L);
        verify(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testDeleteLikePost4() {
        doThrow(new IllegalStateException("foo")).when(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.deleteLikePost(123L));
        verify(this.likeRepository).deleteLikePostByPostMessagePostId((Long) any());
    }

    @Test
    void testUpdateLikePost() {
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
        Optional<LikePost> ofResult = Optional.of(likePost);
        when(this.likeRepository.findById((Long) any())).thenReturn(ofResult);

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

        LikePost likePost1 = new LikePost();
        likePost1.setLikePostId(123L);
        likePost1.setPLike(1L);
        likePost1.setPostMessage(postMessage1);
        this.likeServiceImpl.updateLikePost(likePost1);
        verify(this.likeRepository).findById((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testUpdateLikePost2() {
        when(this.likeRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikePost(likePost));
        verify(this.likeRepository).findById((Long) any());
    }

    @Test
    void testUpdateLikePost3() {
        when(this.likeRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikePost(likePost));
        verify(this.likeRepository).findById((Long) any());
    }

    @Test
    void testUpdateLikePost4() {
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
        Optional<LikePost> ofResult = Optional.of(likePost);
        when(this.likeRepository.findById((Long) any())).thenReturn(ofResult);

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

        LikePost likePost1 = new LikePost();
        likePost1.setLikePostId(123L);
        likePost1.setPLike(1L);
        likePost1.setPostMessage(postMessage1);
        this.likeServiceImpl.updateLikePost(likePost1);
        verify(this.likeRepository).findById((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testUpdateLikePost5() {
        when(this.likeRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikePost(likePost));
        verify(this.likeRepository).findById((Long) any());
    }

    @Test
    void testUpdateLikePost6() {
        when(this.likeRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikePost(likePost));
        verify(this.likeRepository).findById((Long) any());
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
        Optional<LikeComment> ofResult = Optional.of(likeComment);
        when(this.likeCommentRepository.findById((Long) any())).thenReturn(ofResult);

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
        this.likeServiceImpl.updateLikeComment(likeComment1);
        verify(this.likeCommentRepository).findById((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testUpdateLikeComment2() {
        when(this.likeCommentRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikeComment(likeComment));
        verify(this.likeCommentRepository).findById((Long) any());
    }

    @Test
    void testUpdateLikeComment3() {
        when(this.likeCommentRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikeComment(likeComment));
        verify(this.likeCommentRepository).findById((Long) any());
    }

    @Test
    void testUpdateLikeComment4() {
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
        Optional<LikeComment> ofResult = Optional.of(likeComment);
        when(this.likeCommentRepository.findById((Long) any())).thenReturn(ofResult);

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
        this.likeServiceImpl.updateLikeComment(likeComment1);
        verify(this.likeCommentRepository).findById((Long) any());
        assertTrue(this.likeServiceImpl.getAllLikesOfPost().isEmpty());
    }

    @Test
    void testUpdateLikeComment5() {
        when(this.likeCommentRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikeComment(likeComment));
        verify(this.likeCommentRepository).findById((Long) any());
    }

    @Test
    void testUpdateLikeComment6() {
        when(this.likeCommentRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(IllegalStateException.class, () -> this.likeServiceImpl.updateLikeComment(likeComment));
        verify(this.likeCommentRepository).findById((Long) any());
    }
}

