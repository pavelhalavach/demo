package com.demo.service;

import com.demo.dto.GameDTO;
import com.demo.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameServiceJPA {
        Game saveGame(GameDTO gameDTO);
        List<GameDTO> findAllGames();
        Optional<GameDTO> findGameById(Integer id);
        void deleteGame(Integer id);
        Game findByNameNormalized(String name);
}


