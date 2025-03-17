package com.demo.service.impl;

import com.demo.dto.response.CommentDTO;
import com.demo.entity.Comment;
import com.demo.entity.User;
import com.demo.exception.CommentNotFoundException;
import com.demo.repository.CommentRepository;
import com.demo.service.ReviewStatus;
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
    public void saveCommentByAnonUser(User seller, String message, Integer rating) {
        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setRating(rating);
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
    public List<CommentDTO> findAllCommentsBySellerId(Integer id){
        return commentRepository.findAllBySellerId(id)
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
    public ReviewStatus reviewComment(Integer id, boolean decision){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        if (comment.isVerified()){
            return ReviewStatus.ALREADY_VERIFIED;
        }
        if (decision) {
            comment.setVerified(true);
            commentRepository.save(comment);
        } else {
            commentRepository.delete(comment);
        }
        return ReviewStatus.SUCCESS;
    }

    @Override
    public Float findAverageRatingBySellerId(Integer id){
        return commentRepository.findAverageRatingBySellerId(id);
    }

    @Override
    public CommentDTO findCommentByIdAndSellerId(Integer sellerId, Integer commentId) {
        return commentRepository.findCommentByIdAndSellerId(sellerId, commentId)
                .map(seller -> new CommentDTO(
                        seller.getId(),
                        seller.getMessage(),
                        seller.getRating()
                )).orElseThrow(() -> new CommentNotFoundException(sellerId, commentId));
    }
}
