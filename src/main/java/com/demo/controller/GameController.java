package com.demo.controller;

import com.demo.dto.GameDTO;
import com.demo.entity.Game;
import com.demo.service.GameServiceJPA;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    GameServiceJPA gameServiceJPA;

    public GameController(GameServiceJPA gameServiceJPAJPA) {
        this.gameServiceJPA = gameServiceJPAJPA;
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

//    @GetMapping("/games")
//    public String games(Authentication authentication) {
//        System.out.println("Authentication: " + authentication);
//        return "Games";
//    }
//
    @GetMapping("/games")
    public List<GameDTO> findAllGames(){
        return gameServiceJPA.findAllGames();
    }

//    @GetMapping("/games/search/{search}")
//    public List<Game> searchGames(@PathVariable(name="search") String search){
//        return gameServiceJPA.searchGames(search);
//    }

//    @PostMapping("/saveGame")
//    public Game saveGame(@RequestBody Game newGame){
//        return gameServiceJPA.saveGame(newGame);
//    }

    @GetMapping("/games/{id}")
    public Optional<GameDTO> findGameById(@PathVariable(name="id") Integer id){
        return gameServiceJPA.findGameById(id);
    }

    @DeleteMapping("/games/delete/{id}")
    public void deleteGame(@PathVariable(name="id") Integer id){
        gameServiceJPA.deleteGame(id);
    }
}
