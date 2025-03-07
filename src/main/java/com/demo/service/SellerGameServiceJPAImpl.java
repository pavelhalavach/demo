package com.demo.service;

import com.demo.dto.GameDTO;
import com.demo.dto.SellerGameRequestDTO;
import com.demo.entity.Game;
import com.demo.entity.SellerGame;
import com.demo.entity.User;
import com.demo.repository.SellerGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerGameServiceJPAImpl implements SellerGameService {
    private final SellerGameRepository sellerGameRepository;
    private final GameService gameService;

    public SellerGameServiceJPAImpl(SellerGameRepository sellerGameRepository, GameService gameService) {
        this.sellerGameRepository = sellerGameRepository;
        this.gameService = gameService;
    }

    @Override
    public void saveSellerGame(User seller, SellerGameRequestDTO sellerGameRequestDTO) {
        Game gameFromDB = gameService.findByNameNormalized(sellerGameRequestDTO.name());
        if (gameFromDB == null){
            gameFromDB = gameService.saveGame(sellerGameRequestDTO);
        }

        SellerGame sellerGame = new SellerGame();
        sellerGame.setSeller(seller);
        sellerGame.setGame(gameFromDB);
        sellerGame.setDescription(sellerGameRequestDTO.description());
        sellerGameRepository.save(sellerGame);
    }

    @Override
    public List<SellerGame> getSellerGames(User seller) {
        return sellerGameRepository.findBySeller(seller);
    }
}