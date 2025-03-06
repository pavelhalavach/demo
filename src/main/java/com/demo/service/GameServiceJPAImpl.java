package com.demo.service;

import com.demo.dto.GameDTO;
import com.demo.repository.GameRepository;
import com.demo.entity.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceJPAImpl implements GameServiceJPA{

    GameRepository gameRepository;

    public GameServiceJPAImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

//    public Game isGamePresent(GameDTO gameDTO){
//        Game gameFromDB = gameRepository.findByNameNormalized(gameDTO.getName());
//        if (gameFromDB == null){
//            Game game = new Game();
//            game.setName(gameDTO.getName());
//            gameRepository.save(game);
//            games.add(game);
//        } else {
//            games.add(gameFromDB);
//        }
//    }

    @Override
    public Game saveGame(GameDTO gameDTO) {
//        if (gameRepository.existsByName(game.getName())) {
//            throw new IllegalArgumentException("Game with this name already exists!");
//        }
        Game game = new Game();
        game.setName(gameDTO.getName());
        game.setVerified(false);
        return gameRepository.save(game);
    }

    @Override
    public List<GameDTO> findAllGames() {
        return gameRepository.findAll()
                .stream()
                .map(game -> new GameDTO(
                        game.getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GameDTO> findGameById(Integer id) {
        return gameRepository.findById(id)
                .map(game -> new GameDTO(
                        game.getName()
                ));
    }

    @Override
    public void deleteGame(Integer id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game findByNameNormalized(String name) {
        return gameRepository.findByNameNormalized(name);
    }
}
