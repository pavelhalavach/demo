package com.demo.repository;

import com.demo.entity.Game;
import com.demo.entity.SellerOffer;
import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerOfferRepository extends JpaRepository<SellerOffer, Integer> {
    List<SellerOffer> findAllBySellerAndGameIsVerified(User seller, boolean isVerified);
    List<SellerOffer> findAllBySeller(User seller);
    void deleteAllBySellerId(Integer id);
    void deleteBySellerAndGameId(User seller, Integer gameId);
}
