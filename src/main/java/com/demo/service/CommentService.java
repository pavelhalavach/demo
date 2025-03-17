package com.demo.service;

import com.demo.dto.response.CommentDTO;
import com.demo.entity.User;

import java.util.List;

public interface CommentService {
    void saveCommentByAnonUser(User seller, String message, Integer rating);
    List<CommentDTO> findAllVerifiedCommentsBySeller(User seller);
    List<CommentDTO> findAllCommentsBySellerId(Integer id);

    List<CommentDTO> findAllCommentsByIsVerified(boolean isVerified);

    ReviewStatus reviewComment(Integer id, boolean decision);

    Float findAverageRatingBySellerId(Integer id);
    CommentDTO findCommentByIdAndSellerId(Integer sellerId, Integer commentId);
}
