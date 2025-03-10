package com.demo.repository;

import com.demo.entity.Comment;
import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllBySellerAndIsVerified(User seller, boolean isVerified);
    List<Comment> findAllByIsVerified(boolean isVerified);
    Comment findCommentByMessage(String message);

}
