package com.demo.controller;

import com.demo.dto.SellerGameDTO;
import com.demo.dto.UserDTO;
import com.demo.service.SellerGameService;
import com.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anon")
public class AnonUserController {
    private final SellerGameService sellerGameService;
    private final UserService userService;

    public AnonUserController(SellerGameService sellerGameService, UserService userService) {
        this.sellerGameService = sellerGameService;
        this.userService = userService;
    }

    @GetMapping("/sellers/findAll")
    public List<UserDTO> findSellers(){
        return userService.findAllUsers();
    }

    @GetMapping("/sellers/{id}")
    public UserDTO findSellerById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @GetMapping("/sellers/{id}/games")
    public List<SellerGameDTO> findSellerGamesBySellerId(@PathVariable Integer id) {
        return sellerGameService.findAllSellerGamesById(id);
    }


}
