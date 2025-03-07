package com.demo.service.impl;

import com.demo.dto.GameDTO;
import com.demo.dto.SellerGameDTO;
import com.demo.repository.GameRepository;
import com.demo.entity.Game;
import com.demo.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceJPAImpl implements GameService {

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
    public Optional<Game> findById(Integer id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game saveGame(GameDTO gameDTO) {
        Game game = new Game();
        game.setName(gameDTO.getName());
        game.setVerified(false);
        return gameRepository.save(game);
    }

    @Override
    public Game saveGame(SellerGameDTO sellerGameDTO) {
        Game game = new Game();
        game.setName(sellerGameDTO.name());
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
    public GameDTO findGameById(Integer id) {
        return gameRepository.findById(id)
                .map(game -> new GameDTO(
                        game.getName()
                )).orElseThrow(() -> new RuntimeException("Such game is not found"));
    }

    @Override
    public Game findByNameNormalized(String name) {
        return gameRepository.findByNameNormalized(name);
    }

    @Override
    public void deleteGame(Integer id) {
        gameRepository.deleteById(id);
    }
}
