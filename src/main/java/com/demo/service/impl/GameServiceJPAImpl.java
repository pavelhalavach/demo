package com.demo.service.impl;

import com.demo.dto.GameDTO;
import com.demo.dto.SellerOfferDTO;
import com.demo.entity.Comment;
import com.demo.repository.GameRepository;
import com.demo.entity.Game;
import com.demo.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceJPAImpl implements GameService {

    GameRepository gameRepository;

    public GameServiceJPAImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game saveGame(SellerOfferDTO sellerOfferDTO) {
        Game game = new Game();
        game.setName(sellerOfferDTO.name());
        game.setVerified(false);
        return gameRepository.save(game);
    }

    @Override
    public List<GameDTO> findAllGames() {
        return gameRepository.findAll()
                .stream()
                .map(game -> new GameDTO(
                        game.getId(),
                        game.getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDTO> findAllGamesByIsVerified(boolean isVerified) {
        return gameRepository.findAllByIsVerified(isVerified)
                .stream()
                .map(game -> new GameDTO(
                        game.getId(),
                        game.getName()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public GameDTO findGameById(Integer id) {
        return gameRepository.findById(id)
                .map(game -> new GameDTO(
                        game.getId(),
                        game.getName()
                )).orElseThrow(() -> new RuntimeException("Such game is not found"));
    }

    @Override
    public Game findByNameNormalized(String name) {
        return gameRepository.findByNameNormalized(name);
    }

    @Override
    public void reviewGame(Integer id, boolean decision){
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment was not found with id " + id));
        if (decision) {
            game.setVerified(true);
            gameRepository.save(game);
        } else {
            gameRepository.delete(game);
        }
    }

    @Override
    public void deleteGame(Integer id) {
        gameRepository.deleteById(id);
    }
}
