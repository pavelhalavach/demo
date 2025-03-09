package com.demo.service.impl;

import com.demo.dto.CommentDTO;
import com.demo.entity.Comment;
import com.demo.entity.User;
import com.demo.repository.CommentRepository;
import com.demo.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceJPAImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceJPAImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public void saveComment(User seller, CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setMessage(commentDTO.message());
        comment.setRating(commentDTO.rating());
        comment.setVerified(false);
        comment.setSeller(seller);

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> findAllCommentsBySeller(User seller) {
        return commentRepository.findAllBySellerAndIsVerified(seller, true)
                .stream()
                .map(comment -> new CommentDTO(
                        comment.getMessage(),
                        comment.getRating()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public List<CommentDTO> findAllCommentsForAdmin(){
        return commentRepository.findAllByIsVerified(false)
                .stream()
                .map(comment -> new CommentDTO(
                        comment.getMessage(),
                        comment.getRating()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Comment findCommentByMessage(String message){
        return commentRepository.findCommentByMessage(message);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
    @Override
    public CommentDTO findCommentById(Integer id) {
        return null;
    }

    @Override
    public void deleteCommentById(Integer id) {


    }
}
