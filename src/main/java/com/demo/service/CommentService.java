package com.demo.service;

import com.demo.dto.CommentDTO;
import com.demo.dto.GameDTO;
import com.demo.entity.User;

import java.util.List;

public interface CommentService {
    void saveComment(User seller, CommentDTO commentDTO);
    List<CommentDTO> findAllCommentsBySeller(User seller);
    GameDTO findCommentById(Integer id);
    void deleteCommentById(Integer id);
}
