package com.demo.controller;

import com.demo.dto.GameDTO;
import com.demo.dto.RegisterSellerRequestDTO;
import com.demo.service.GameService;
import com.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RegisterController {
    private final UserService userService;
    private final GameService gameService;

    public RegisterController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/register")
    public Map<String, Object> info(){
        List<GameDTO> games = gameService.findAllGames();
        Map<String, Object> response = new HashMap<>();
        response.put("games", games);
//        response.put("message", """
//                Welcome! You can register a Seller Profile and select Games in which you sell items.\s
//                If you can't find needed Game you can also add it.\s
//                It will take some time until the Administrator approves it.\s
//                Available games are:\s
//                """);
        return response;
    }

    @PostMapping("/register")
    public String saveUser(@RequestBody RegisterSellerRequestDTO registerSellerRequestDTO){
        userService.saveUser(registerSellerRequestDTO);
        return "register";
    }
}
