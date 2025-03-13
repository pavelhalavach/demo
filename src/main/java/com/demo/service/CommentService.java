package com.demo.service;

import com.demo.dto.CommentDTO;
import com.demo.entity.User;

import java.util.List;

public interface CommentService {
    void saveCommentByAnonUser(User seller, CommentDTO commentDTO);
    List<CommentDTO> findAllVerifiedCommentsBySeller(User seller);
    List<CommentDTO> findAllCommentsBySeller(User seller);

    List<CommentDTO> findAllCommentsByIsVerified(boolean isVerified);

    void reviewComment(Integer id, boolean decision);

    Float findAverageRatingBySellerId(Integer id);
}
