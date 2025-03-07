package com.demo.service;

import com.demo.dto.SellerGameDTO;
import com.demo.entity.Game;
import com.demo.entity.SellerGame;
import com.demo.entity.User;

import java.util.List;

public interface SellerGameService {
    void saveSellerGame(User seller, SellerGameDTO sellerGameDTOO);
    List<SellerGameDTO> findAllSellerGames(User seller);
    List<SellerGameDTO> findAllSellerGamesById(Integer id);
}
