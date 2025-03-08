package com.demo.controller;

import com.demo.dto.*;
import com.demo.service.GameService;
import com.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegisterController {
    private final UserService userService;
    private final GameService gameService;

    public RegisterController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/register")
    public RegisterInfoDTO giveInfoForSeller(){
        List<GameDTO> games = gameService.findAllGames();
        String message = "Welcome! You can register a Seller Profile and select Games in which you sell items. " +
                "If you can't find needed Game you can also add it. " +
                "It will take some time until the Administrator approves it. " +
                "Available games are: ";
        return new RegisterInfoDTO(message, games);
    }

    @PostMapping("/register")
    public String saveSeller(@RequestBody RegisterSellerRequestDTO registerSellerRequestDTO){
        userService.saveSeller(registerSellerRequestDTO);
        return "register";
    }


}
