package com.demo.controller;

import com.demo.dto.SellerGameDTO;
import com.demo.entity.SellerGame;
import com.demo.entity.User;
import com.demo.security.CustomUserDetails;
import com.demo.service.SellerGameService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerGameController {
    private final SellerGameService sellerGameService;

    public SellerGameController(SellerGameService sellerGameService) {
        this.sellerGameService = sellerGameService;
    }

    @PostMapping("/saveGame")
    public String addGame(@RequestBody SellerGameDTO sellerGameDTO, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User seller = customUserDetails.getUser();
        sellerGameService.saveSellerGame(seller, sellerGameDTO);
        return "Game saved";
    }

    @GetMapping("/findGames")
    public List<SellerGameDTO> getGames(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User seller = customUserDetails.getUser();
        return sellerGameService.findAllSellerGames(seller);
    }
}
