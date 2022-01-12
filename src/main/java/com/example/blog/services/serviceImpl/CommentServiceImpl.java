package com.example.blog.services.serviceImpl;

import com.example.blog.model.Comment;
import com.example.blog.repository.CommentRepository;
import com.example.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentById(Long postId){
        return commentRepository.findByPostMessagePostId(postId);
    }


    public boolean addNewComment(Comment comment) {
        commentRepository.save(comment);
        return true;
    }

    public Comment getCommentById(Long commentId){
        return commentRepository.findById(commentId).get();
    }


    public void deleteComment(Long commentId) {
        boolean exist = commentRepository.existsById(commentId);
        if(!exist){
            throw  new IllegalStateException("Comment with id -> " + commentId + " does not exist");
        }
        commentRepository.deleteById(commentId);
    }


    public void deleteAllByPostId(Long postId){
        commentRepository.deleteAllByPostMessagePostId(postId);
    }

//    @Transactional
    public void updateComment(Comment comment) {
        Comment comment1 = commentRepository.findById(comment.getCommentId()).orElseThrow(() -> new IllegalStateException("does not exist"));
        comment1.setCommentBody(comment.getCommentBody());
    }

    @Override
    public List<Comment> allCommentContains(String comment) {
        return commentRepository.getAllByCommentBodyContains(comment);
    }
}
