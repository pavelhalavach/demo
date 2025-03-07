package com.demo.service;

import com.demo.dto.SellerGameRequestDTO;
import com.demo.entity.SellerGame;
import com.demo.entity.User;

import java.util.List;

public interface SellerGameService {
    void saveSellerGame(User seller, SellerGameRequestDTO sellerGameRequestDTOO);
    List<SellerGame> getSellerGames(User seller);

}
