package com.demo.service;

import com.demo.dto.CommentDTO;
import com.demo.entity.Comment;
import com.demo.entity.User;

import java.util.List;

public interface CommentService {
    void saveComment(User seller, CommentDTO commentDTO);
    List<CommentDTO> findAllCommentsBySeller(User seller);

    List<CommentDTO> findAllCommentsForAdmin();

    Comment findCommentByMessage(String message);

    CommentDTO findCommentById(Integer id);
    void deleteCommentById(Integer id);

    void deleteComment(Comment comment);
}
