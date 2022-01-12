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
import com.example.blog.model.PostMessage;
import com.example.blog.model.Users;
import com.example.blog.repository.CommentRepository;

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

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Test
    void testGetAllComment() {
        ArrayList<Comment> commentList = new ArrayList<>();
        when(this.commentRepository.findAll()).thenReturn(commentList);
        List<Comment> actualAllComment = this.commentServiceImpl.getAllComment();
        assertSame(commentList, actualAllComment);
        assertTrue(actualAllComment.isEmpty());
        verify(this.commentRepository).findAll();
    }

    @Test
    void testGetAllComment2() {
        when(this.commentRepository.findAll()).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.getAllComment());
        verify(this.commentRepository).findAll();
    }

    @Test
    void testGetAllCommentById() {
        ArrayList<Comment> commentList = new ArrayList<>();
        when(this.commentRepository.findByPostMessagePostId((Long) any())).thenReturn(commentList);
        List<Comment> actualAllCommentById = this.commentServiceImpl.getAllCommentById(123L);
        assertSame(commentList, actualAllCommentById);
        assertTrue(actualAllCommentById.isEmpty());
        verify(this.commentRepository).findByPostMessagePostId((Long) any());
        assertTrue(this.commentServiceImpl.getAllComment().isEmpty());
    }

    @Test
    void testGetAllCommentById2() {
        when(this.commentRepository.findByPostMessagePostId((Long) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.getAllCommentById(123L));
        verify(this.commentRepository).findByPostMessagePostId((Long) any());
    }

    @Test
    void testAddNewComment() {
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
        when(this.commentRepository.save((Comment) any())).thenReturn(comment);

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
        assertTrue(this.commentServiceImpl.addNewComment(comment1));
        verify(this.commentRepository).save((Comment) any());
        assertTrue(this.commentServiceImpl.getAllComment().isEmpty());
    }

    @Test
    void testAddNewComment2() {
        when(this.commentRepository.save((Comment) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.addNewComment(comment));
        verify(this.commentRepository).save((Comment) any());
    }

    @Test
    void testGetCommentById() {
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
        Optional<Comment> ofResult = Optional.of(comment);
        when(this.commentRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(comment, this.commentServiceImpl.getCommentById(123L));
        verify(this.commentRepository).findById((Long) any());
        assertTrue(this.commentServiceImpl.getAllComment().isEmpty());
    }

    @Test
    void testGetCommentById2() {
        when(this.commentRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.getCommentById(123L));
        verify(this.commentRepository).findById((Long) any());
    }

    @Test
    void testDeleteComment() {
        doNothing().when(this.commentRepository).deleteById((Long) any());
        when(this.commentRepository.existsById((Long) any())).thenReturn(true);
        this.commentServiceImpl.deleteComment(123L);
        verify(this.commentRepository).deleteById((Long) any());
        verify(this.commentRepository).existsById((Long) any());
        assertTrue(this.commentServiceImpl.getAllComment().isEmpty());
    }

    @Test
    void testDeleteComment2() {
        doThrow(new IllegalStateException("foo")).when(this.commentRepository).deleteById((Long) any());
        when(this.commentRepository.existsById((Long) any())).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.deleteComment(123L));
        verify(this.commentRepository).deleteById((Long) any());
        verify(this.commentRepository).existsById((Long) any());
    }

    @Test
    void testDeleteComment3() {
        doNothing().when(this.commentRepository).deleteById((Long) any());
        when(this.commentRepository.existsById((Long) any())).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.deleteComment(123L));
        verify(this.commentRepository).existsById((Long) any());
    }

    @Test
    void testDeleteAllByPostId() {
        when(this.commentRepository.deleteAllByPostMessagePostId((Long) any())).thenReturn(123);
        this.commentServiceImpl.deleteAllByPostId(123L);
        verify(this.commentRepository).deleteAllByPostMessagePostId((Long) any());
        assertTrue(this.commentServiceImpl.getAllComment().isEmpty());
    }

    @Test
    void testDeleteAllByPostId2() {
        when(this.commentRepository.deleteAllByPostMessagePostId((Long) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.deleteAllByPostId(123L));
        verify(this.commentRepository).deleteAllByPostMessagePostId((Long) any());
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
        Optional<Comment> ofResult = Optional.of(comment);
        when(this.commentRepository.findById((Long) any())).thenReturn(ofResult);

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
        this.commentServiceImpl.updateComment(comment1);
        verify(this.commentRepository).findById((Long) any());
        assertTrue(this.commentServiceImpl.getAllComment().isEmpty());
    }

    @Test
    void testUpdateComment2() {
        when(this.commentRepository.findById((Long) any())).thenThrow(new IllegalStateException("foo"));

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
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.updateComment(comment));
        verify(this.commentRepository).findById((Long) any());
    }

    @Test
    void testUpdateComment3() {
        when(this.commentRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.updateComment(comment));
        verify(this.commentRepository).findById((Long) any());
    }

    @Test
    void testAllCommentContains() {
        ArrayList<Comment> commentList = new ArrayList<>();
        when(this.commentRepository.getAllByCommentBodyContains((String) any())).thenReturn(commentList);
        List<Comment> actualAllCommentContainsResult = this.commentServiceImpl.allCommentContains("Comment");
        assertSame(commentList, actualAllCommentContainsResult);
        assertTrue(actualAllCommentContainsResult.isEmpty());
        verify(this.commentRepository).getAllByCommentBodyContains((String) any());
        assertTrue(this.commentServiceImpl.getAllComment().isEmpty());
    }

    @Test
    void testAllCommentContains2() {
        when(this.commentRepository.getAllByCommentBodyContains((String) any()))
                .thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.commentServiceImpl.allCommentContains("Comment"));
        verify(this.commentRepository).getAllByCommentBodyContains((String) any());
    }
}

