package com.demo.repository;

import com.demo.entity.Comment;
import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllBySellerAndIsVerified(User seller, boolean isVerified);
    List<Comment> findAllBySeller(User seller);
    List<Comment> findAllByIsVerified(boolean isVerified);
    @Query("SELECT AVG(c.rating) FROM Comment c WHERE c.seller.id = :sellerId AND c.isVerified = true")
    Float findAverageRatingBySellerId(@Param("sellerId") Integer sellerId);
}
