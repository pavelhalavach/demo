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
    public void saveCommentByAnonUser(User seller, CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setMessage(commentDTO.message());
        comment.setRating(commentDTO.rating());
        comment.setVerified(false);
        comment.setSeller(seller);

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> findAllVerifiedCommentsBySeller(User seller) {
        return commentRepository.findAllBySellerAndIsVerified(seller, true)
                .stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getMessage(),
                        comment.getRating()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public List<CommentDTO> findAllCommentsBySeller(User seller){
        return commentRepository.findAllBySeller(seller)
                .stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getMessage(),
                        comment.getRating()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> findAllCommentsByIsVerified(boolean isVerified) {
        return commentRepository.findAllByIsVerified(isVerified)
                .stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getMessage(),
                        comment.getRating()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void reviewComment(Integer id, boolean decision){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment was not found with id " + id));
        if (decision) {
            comment.setVerified(true);
            commentRepository.save(comment);
        } else {
            commentRepository.delete(comment);
        }
    }

    @Override
    public Float findAverageRatingBySellerId(Integer id){
        return commentRepository.findAverageRatingBySellerId(id);
    }
}
