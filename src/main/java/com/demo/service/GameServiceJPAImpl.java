package com.demo.service;

import com.demo.dto.GameDTO;
import com.demo.dto.SellerGameRequestDTO;
import com.demo.repository.GameRepository;
import com.demo.entity.Game;
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
    public Game saveGame(SellerGameRequestDTO sellerGameRequestDTO) {
        Game game = new Game();
        game.setName(sellerGameRequestDTO.name());
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
