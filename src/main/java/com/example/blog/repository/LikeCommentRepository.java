package com.example.blog.repository;

import com.example.blog.model.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    LikeComment getLikeCommentByComment_CommentId(Long commentId);

}
