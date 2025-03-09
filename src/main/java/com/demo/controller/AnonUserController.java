package com.demo.controller;

import com.demo.dto.AddCommentRequestDTO;
import com.demo.dto.SellerDTO;
import com.demo.dto.SellerGameDTO;
import com.demo.service.SellerGameService;
import com.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    public List<SellerDTO> findSellers(){
        return userService.findAllSellers(true);
    }


//    ???
//    @GetMapping("/sellers/{id}")
//    public SellerDTO findSellerById(@PathVariable Integer id) {
//        return userService.findSellerById(id);
//    }
//
//    @GetMapping("/sellers/{id}/games")
//    public List<SellerGameDTO> findSellerGamesBySellerId(@PathVariable Integer id) {
//        return sellerGameService.findAllSellerGamesById(id);
//    }

    @PostMapping("/sellers/addComment")
    public String addComment(@RequestBody AddCommentRequestDTO addCommentRequestDTO){
        userService.saveSellerByAnonUser(addCommentRequestDTO.sellerDTO(), addCommentRequestDTO.commentDTO());
        return "register";
    }


}
