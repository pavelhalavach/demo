package com.demo.service;

import com.demo.dto.SellerOfferDTO;
import com.demo.entity.User;

import java.util.List;

public interface SellerOfferService {
    void saveSellerOffer(User seller, SellerOfferDTO sellerOfferDTOO);
    List<SellerOfferDTO> findAllSellerOffersDTO(User seller);
    List<SellerOfferDTO> findAllVerifiedSellerOffers(User seller);
    boolean isVerifiedSellerOfferExistByGameId(User seller, Integer id);
    void deleteAllByUserId(Integer id);
    void deleteBySellerAndGameId(User seller, Integer gameId);
    void updateOfferDescription(User seller, Integer gameId, String description);
}
