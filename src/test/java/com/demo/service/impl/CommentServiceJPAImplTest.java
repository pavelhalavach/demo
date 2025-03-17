package com.demo.service.impl;

import com.demo.dto.response.CommentDTO;
import com.demo.entity.Comment;
import com.demo.entity.User;
import com.demo.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceJPAImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceJPAImpl commentService;

    @Test
    public void shouldSaveCommentByAnonUser() {
        User seller = new User();
        commentService.saveCommentByAnonUser(seller, "Great product!", 5);
        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);

        verify(commentRepository, times(1)).save(captor.capture());

        Comment actual = captor.getValue();
        assertEquals("Great product!", actual.getMessage());
        assertEquals(seller, actual.getSeller());
        assertEquals(5, actual.getRating());
    }

    @Test
    public void shouldReturnAllVerifiedCommentsBySeller() {
        User seller = new User();
        Comment comment = new Comment(2, "Excellent", 5, seller, true, new Date());
        when(commentRepository.findAllBySellerAndIsVerified(seller, true)).thenReturn(Arrays.asList(comment));
        List<CommentDTO> result = commentService.findAllVerifiedCommentsBySeller(seller);

        assertEquals(1, result.size());
    }

    @Test
    public void shouldFindAllCommentsBySellerId() {
        User seller = new User();
        Comment comment1 = new Comment(1, "Good", 4, seller, false, new Date());
        Comment comment2 = new Comment(2, "Excellent", 5, seller, true, new Date());
        when(commentRepository.findAllBySellerId(seller.getId())).thenReturn(Arrays.asList(comment1, comment2));
        List<CommentDTO> result = commentService.findAllCommentsBySellerId(seller.getId());

        assertEquals(2, result.size());
    }

    @Test
    public void shouldFindAllCommentsByIsVerified() {
        Comment comment = new Comment(2, "Excellent", 5, new User(), true, new Date());
        when(commentRepository.findAllByIsVerified(true)).thenReturn(Arrays.asList(comment));
        List<CommentDTO> result = commentService.findAllCommentsByIsVerified(true);

        assertEquals(1, result.size());
    }

    @Test
    public void shouldReviewCommentAndApprove() {
        Comment comment = new Comment(1, "Good", 4, new User(), false, new Date());
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        commentService.reviewComment(1, true);

        verify(commentRepository, times(1)).findById(1);
        verify(commentRepository, times(1)).save(comment);
        verify(commentRepository, times(0)).delete(comment);
        assertTrue(comment.isVerified());
    }

    @Test
    public void shouldReviewCommentAndReject() {
        Comment comment = new Comment(1, "Good", 4, new User(), false, new Date());
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        commentService.reviewComment(1, false);

        verify(commentRepository, times(1)).findById(1);
        verify(commentRepository, times(1)).delete(comment);
        verify(commentRepository, times(0)).save(comment);
    }

    @Test
    public void shouldFindAverageRatingBySellerId() {
        when(commentRepository.findAverageRatingBySellerId(1)).thenReturn(4.5f);
        Float result = commentService.findAverageRatingBySellerId(1);
        assertEquals(4.5f, result);
    }
}
