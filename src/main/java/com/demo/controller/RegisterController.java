package com.demo.controller;

import com.demo.dto.request.RegisterAdminRequestDTO;
import com.demo.dto.request.RegisterSellerRequestDTO;
import com.demo.dto.response.GameDTO;
import com.demo.dto.response.RegisterInfoDTO;
import com.demo.service.GameService;
import com.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/register/seller")
    public RegisterInfoDTO giveInfoForSeller(){
        List<GameDTO> games = gameService.findAllGames();
        String message = "Welcome! You can register a Seller Profile and select Games in which you sell items. " +
                "If you can't find needed Game you can also add it. " +
                "It will take some time until the Administrator approves it. " +
                "Available games are: ";
        return new RegisterInfoDTO(message, games);
    }

    @PostMapping("/register/seller")
    public ResponseEntity<String> saveSeller(@RequestBody @Valid RegisterSellerRequestDTO registerSellerRequestDTO){
        userService.saveSeller(registerSellerRequestDTO);
        return ResponseEntity.ok("Successfully registered");
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> saveAdmin(@RequestBody @Valid RegisterAdminRequestDTO registerAdminRequestDTO){
        userService.saveAdmin(registerAdminRequestDTO);
        return ResponseEntity.ok("Successfully registered");
    }
}
