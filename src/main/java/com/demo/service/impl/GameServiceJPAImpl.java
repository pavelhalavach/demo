package com.demo.service.impl;

import com.demo.dto.response.GameDTO;
import com.demo.dto.request.RegisterSellerOfferDTO;
import com.demo.exception.GameNotFoundException;
import com.demo.repository.GameRepository;
import com.demo.entity.Game;
import com.demo.service.GameService;
import com.demo.service.ReviewStatus;
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
    public Game saveGame(RegisterSellerOfferDTO registerSellerOfferDTO) {
        Game game = new Game();
        game.setName(registerSellerOfferDTO.name());
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
                )).orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public Game findByNameNormalized(String name) {
        return gameRepository.findByNameNormalized(name);
    }

    @Override
    public ReviewStatus reviewGame(Integer id, boolean decision){
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
        if (game.isVerified()){
            return ReviewStatus.ALREADY_VERIFIED;
        }
        if (decision) {
            game.setVerified(true);
            gameRepository.save(game);
        } else {
            gameRepository.delete(game);
        }
        return ReviewStatus.SUCCESS;
    }

}
