package com.demo.controller;


import com.demo.dto.CommentDTO;
import com.demo.dto.GameDTO;
import com.demo.dto.SellerDTO;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.security.CustomUserDetails;
import com.demo.service.CommentService;
import com.demo.service.GameService;
import com.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CommentService commentService;
    private final GameService gameService;

    public AdminController(UserService userService, CommentService commentService, GameService gameService) {
        this.userService = userService;
        this.commentService = commentService;
        this.gameService = gameService;
    }

    @GetMapping("/sellers")
    public List<SellerDTO> findNonVerifiedSellers(){
        return userService.findAllUsersByIsVerified(Role.SELLER,false);
    }

    @GetMapping("/admins")
    public List<SellerDTO> findNonVerifiedAdmins(){
        return userService.findAllUsersByIsVerified(Role.ADMIN,false);
    }

    @GetMapping("/comments")
    public List<CommentDTO> findNonVerifiedComments(){
        return commentService.findAllCommentsByIsVerified(false);
    }

    @GetMapping("/games")
    public List<GameDTO> findNonVerifiedGames(){
        return gameService.findAllGamesByIsVerified(false);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<String> reviewUser(@PathVariable(name="id") Integer id,
                                             @RequestBody boolean decision,
                                             Authentication authentication
    ){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User admin = customUserDetails.getUser();

        if (admin.isVerified()) {
            userService.reviewUser(id, decision);
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not verified to do that. Please contact Head Admin");
        }
        return ResponseEntity.ok("Successfully reviewed");
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<String> reviewComment(@PathVariable Integer id,
                                                @RequestBody boolean decision,
                                                Authentication authentication
    ){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User admin = customUserDetails.getUser();

        if (admin.isVerified()) {
            commentService.reviewComment(id, decision);
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not verified to do that. Please contact Head Admin");
        }
        return ResponseEntity.ok("Successfully reviewed");
    }

    @PostMapping("/game/{id}")
    public ResponseEntity<String> reviewGame(@PathVariable Integer id,
                                            @RequestBody boolean decision,
                                            Authentication authentication
    ){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User admin = customUserDetails.getUser();

        if (admin.isVerified()) {
            gameService.reviewGame(id, decision);
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You are not verified to do that. Please contact Head Admin");
        }
        return ResponseEntity.ok("Successfully reviewed");
    }
}
