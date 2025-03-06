package com.demo.controller;

import com.demo.dto.GameDTO;
import com.demo.dto.RegisterRequestDTO;
import com.demo.service.GameServiceJPA;
import com.demo.service.UserServiceJPA;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RegisterController {
    private final UserServiceJPA userServiceJPA;
    private final GameServiceJPA gameServiceJPA;

    public RegisterController(UserServiceJPA userServiceJPA, GameServiceJPA gameServiceJPA) {
        this.userServiceJPA = userServiceJPA;
        this.gameServiceJPA = gameServiceJPA;
    }

    @GetMapping("/register")
    public Map<String, Object> info(){
        List<GameDTO> games = gameServiceJPA.findAllGames();
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
    public String saveUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        userServiceJPA.saveUser(registerRequestDTO);
        return "register";
    }
}
