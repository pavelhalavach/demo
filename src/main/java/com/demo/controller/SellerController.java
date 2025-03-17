package com.demo.controller;

import com.demo.dto.request.RegisterSellerOfferDTO;
import com.demo.dto.response.SellerOfferDTO;
import com.demo.entity.User;
import com.demo.security.CustomUserDetails;
import com.demo.service.SellerOfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    private final SellerOfferService sellerOfferService;

    public SellerController(SellerOfferService sellerOfferService) {
        this.sellerOfferService = sellerOfferService;
    }

    @GetMapping("/games")
    public List<SellerOfferDTO> getGames(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User seller = customUserDetails.getUser();
        return sellerOfferService.findAllVerifiedSellerOffers(seller);
    }

    @PostMapping("/game")
    public ResponseEntity<String> addGame(
            @RequestBody RegisterSellerOfferDTO registerSellerOfferDTO,
            Authentication authentication
    ) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User seller = customUserDetails.getUser();
        sellerOfferService.saveSellerOffer(seller, registerSellerOfferDTO);
        return ResponseEntity.ok("Game successfully added");
    }

    @DeleteMapping("/game/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable(name="id") Integer id, Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User seller = customUserDetails.getUser();
        sellerOfferService.deleteBySellerAndGameId(seller, id);
        return ResponseEntity.ok("Game successfully deleted");
    }

    @PutMapping("/game/{id}")
    public ResponseEntity<String> updateGame(
            @PathVariable Integer id,
            @RequestBody String updatedDescription,
            Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User seller = customUserDetails.getUser();
        sellerOfferService.updateOfferDescription(seller, id, updatedDescription);
        return ResponseEntity.ok("Game successfully updated");
    }
}
