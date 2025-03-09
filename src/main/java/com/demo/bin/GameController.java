package com.demo.bin;

import com.demo.dto.GameDTO;
import com.demo.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameServiceJPAJPA) {
        this.gameService = gameServiceJPAJPA;
    }

//    @GetMapping("/home")
//    public String home(){
//        return "home";
//    }
//
////    @GetMapping("/games")
////    public String games(Authentication authentication) {
////        System.out.println("Authentication: " + authentication);
////        return "Games";
////    }
////
//    @GetMapping("/games")
//    public List<GameDTO> findAllGames(){
//        return gameService.findAllGames();
//    }

//    @GetMapping("/games/search/{search}")
//    public List<Game> searchGames(@PathVariable(name="search") String search){
//        return gameService.searchGames(search);
//    }

//    @PostMapping("/saveGame")
//    public Game saveGame(@RequestBody Game newGame){
//        return gameService.saveGame(newGame);
//    }

//    @GetMapping("/games/{id}")
//    public GameDTO findGameById(@PathVariable(name="id") Integer id){
//        return gameService.findGameById(id);
//    }
//
//    @DeleteMapping("/games/delete/{id}")
//    public void deleteGame(@PathVariable(name="id") Integer id){
//        gameService.deleteGame(id);
//    }
}
