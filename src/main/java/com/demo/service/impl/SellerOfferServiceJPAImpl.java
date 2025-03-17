package com.demo.service.impl;

import com.demo.dto.request.RegisterSellerOfferDTO;
import com.demo.dto.response.SellerOfferDTO;
import com.demo.entity.Game;
import com.demo.entity.SellerOffer;
import com.demo.entity.User;
import com.demo.repository.SellerOfferRepository;
import com.demo.service.GameService;
import com.demo.service.SellerOfferService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerOfferServiceJPAImpl implements SellerOfferService {
    private final SellerOfferRepository sellerOfferRepository;
    private final GameService gameService;

    public SellerOfferServiceJPAImpl(SellerOfferRepository sellerOfferRepository, GameService gameService) {
        this.sellerOfferRepository = sellerOfferRepository;
        this.gameService = gameService;
    }

    @Override
    public void saveSellerOffer(User seller, RegisterSellerOfferDTO registerSellerOfferDTO) {
        Game gameFromDB = gameService.findByNameNormalized(registerSellerOfferDTO.name());
        if (gameFromDB == null){
            gameFromDB = gameService.saveGame(registerSellerOfferDTO);
        }
        sellerOfferRepository.save(new SellerOffer(seller, gameFromDB, registerSellerOfferDTO.description()));
    }

    @Override
    public List<SellerOfferDTO> findAllSellerOffersDTO(User seller) {
        return sellerOfferRepository.findAllBySeller(seller)
                .stream()
                .map(sellerOffer -> new SellerOfferDTO(
                        sellerOffer.getGame().getId(),
                        sellerOffer.getGame().getName(),
                        sellerOffer.getDescription()
                )).collect(Collectors.toList());
    }

    @Override
    public List<SellerOfferDTO> findAllVerifiedSellerOffers(User seller) {
        return sellerOfferRepository.findAllBySellerAndGameIsVerified(seller, true)
                .stream()
                .map(sellerOffer -> new SellerOfferDTO(
                        sellerOffer.getGame().getId(),
                        sellerOffer.getGame().getName(),
                        sellerOffer.getDescription()
                )).collect(Collectors.toList());
    }

    @Override
    public boolean isVerifiedSellerOfferExistByGameId(User seller, Integer id){
        List<SellerOffer> sellerOffers = sellerOfferRepository.findAllBySellerAndGameIsVerified(seller, true);
        for (SellerOffer sellerOffer : sellerOffers){
            if(sellerOffer.getGame().getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteBySellerAndGameId(User seller, Integer gameId){
        sellerOfferRepository.deleteBySellerAndGameId(seller, gameId);
    }

    @Override
    public void updateOfferDescription(User seller, Integer gameId, String description){
        Game game = new Game();
        game.setId(gameId);
        game.setName(gameService.findGameById(gameId).name());
        sellerOfferRepository.save(new SellerOffer(seller, game, description));
    }

}