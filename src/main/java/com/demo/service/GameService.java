package com.demo.service;

import com.demo.dto.GameDTO;
import com.demo.dto.SellerOfferDTO;
import com.demo.entity.Game;

import java.util.List;

public interface GameService {
        Game saveGame(SellerOfferDTO sellerOfferDTO);
        List<GameDTO> findAllGames();

    List<GameDTO> findAllGamesByIsVerified(boolean isVerified);

    GameDTO findGameById(Integer id);
        void deleteGame(Integer id);

        Game findByNameNormalized(String name);

    void reviewGame(Integer id, boolean decision);
}


