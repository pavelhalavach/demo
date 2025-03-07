package com.demo.service.impl;

import com.demo.dto.SellerGameDTO;
import com.demo.entity.Game;
import com.demo.entity.SellerGame;
import com.demo.entity.User;
import com.demo.repository.SellerGameRepository;
import com.demo.service.GameService;
import com.demo.service.SellerGameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerGameServiceJPAImpl implements SellerGameService {
    private final SellerGameRepository sellerGameRepository;
    private final GameService gameService;

    public SellerGameServiceJPAImpl(SellerGameRepository sellerGameRepository, GameService gameService) {
        this.sellerGameRepository = sellerGameRepository;
        this.gameService = gameService;
    }

    @Override
    public void saveSellerGame(User seller, SellerGameDTO sellerGameDTO) {
        Game gameFromDB = gameService.findByNameNormalized(sellerGameDTO.name());
        if (gameFromDB == null){
            gameFromDB = gameService.saveGame(sellerGameDTO);
        }
        sellerGameRepository.save(new SellerGame(seller, gameFromDB, sellerGameDTO.description()));
    }

    @Override
    public List<SellerGameDTO> findAllSellerGames(User seller) {
        return sellerGameRepository.findBySeller(seller)
                .stream()
                .map(sellerGame -> new SellerGameDTO(
                        sellerGame.getGame().getName(),
                        sellerGame.getDescription()
                )).collect(Collectors.toList());
    }

    public List<SellerGameDTO> findAllSellerGamesById(Integer id) {
        return sellerGameRepository.findSellerGameBySellerId(id)
                .stream()
                .map(sellerGame -> new SellerGameDTO(
                        sellerGame.getGame().getName(),
                        sellerGame.getDescription()
                )).collect(Collectors.toList());
    }

}