package com.demo.controller;

import com.demo.dto.SellerGameRequestDTO;
import com.demo.entity.SellerGame;
import com.demo.entity.User;
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

    @PostMapping("/addGame")
    public String addGame(@RequestBody SellerGameRequestDTO sellerGameRequestDTO, Authentication authentication) {
        User seller = (User) authentication.getPrincipal();
        sellerGameService.saveSellerGame(seller, sellerGameRequestDTO);
        return "Game added";
    }

    @GetMapping("/getGames")
    public List<SellerGame> getGames(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return sellerGameService.getSellerGames(user);
    }
}
