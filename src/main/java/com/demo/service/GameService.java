package com.demo.service;

import com.demo.dto.GameDTO;
import com.demo.dto.SellerGameRequestDTO;
import com.demo.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {
        Optional<Game> findById(Integer id);
        Game saveGame(GameDTO gameDTO);
        List<GameDTO> findAllGames();
        Optional<GameDTO> findGameById(Integer id);
        void deleteGame(Integer id);
        Game findByNameNormalized(String name);

        Game saveGame(SellerGameRequestDTO sellerGameRequestDTO);
}


