package com.example.blog.services.serviceImpl;

import com.example.blog.model.Comment;
import com.example.blog.model.LikeComment;
import com.example.blog.model.LikePost;
import com.example.blog.model.PostMessage;
import com.example.blog.repository.LikeCommentRepository;
import com.example.blog.repository.LikeRepository;
import com.example.blog.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, LikeCommentRepository likeCommentRepository) {
        this.likeRepository = likeRepository;
        this.likeCommentRepository = likeCommentRepository;
    }

    public List<LikePost> getAllLikesOfPost(){
        return likeRepository.findAll();
    }

    @Override
    public void addPost(PostMessage postMessage) {
        LikePost likePost = new LikePost();
        likePost.setPLike(0L);
        likePost.setPostMessage(postMessage);
        likeRepository.save(likePost);
    }

    @Override
    public LikeComment getCommentLikeById(Long commetId) {
        return likeCommentRepository.getLikeCommentByComment_CommentId(commetId);
    }

    @Override
    public void addCommentLike(Comment comment) {
        LikeComment likeComment = new LikeComment();
        likeComment.setComment(comment);
        likeComment.setCLike(0L);
        likeCommentRepository.save(likeComment);
    }


    public LikePost checkIfExists(Long postId){
        return likeRepository.getLikePostByPostMessagePostId(postId);
    }

    public boolean addLikePost(LikePost likePost){
        likeRepository.save(likePost);
        return true;
    }


    public void deleteLikePost(Long postId){
        likeRepository.deleteLikePostByPostMessagePostId(postId);
    }

    @Override
    public void updateLikePost(LikePost likePost) {
        LikePost likePost1 = likeRepository.findById(likePost.getLikePostId()).orElseThrow(() -> new IllegalStateException("does not exist"));
        likePost1.setPLike(likePost.getPLike());
    }

    @Override
    public void updateLikeComment(LikeComment likeComment) {
        LikeComment likeComment1 = likeCommentRepository.findById(likeComment.getLikeCommentId())
                .orElseThrow(() -> new IllegalStateException("does not exist"));
        likeComment1.setCLike(likeComment.getCLike());
    }
}
