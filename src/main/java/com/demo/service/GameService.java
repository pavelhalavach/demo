package com.demo.service;

import com.demo.dto.response.GameDTO;
import com.demo.dto.request.RegisterSellerOfferDTO;
import com.demo.entity.Game;

import java.util.List;

public interface GameService {
    Game saveGame(RegisterSellerOfferDTO registerSellerOfferDTO);
    List<GameDTO> findAllGames();
    List<GameDTO> findAllGamesByIsVerified(boolean isVerified);
    GameDTO findGameById(Integer id);
    Game findGameByNameNormalized(String name);
    ReviewStatus reviewGame(Integer id, boolean decision);
}


