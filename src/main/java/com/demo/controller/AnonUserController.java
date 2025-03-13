package com.demo.controller;

import com.demo.dto.AddCommentRequestDTO;
import com.demo.dto.CommentDTO;
import com.demo.dto.SellerDTO;
import com.demo.dto.ShowTopSellersResponseDTO;
import com.demo.entity.Role;
import com.demo.service.SellerOfferService;
import com.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anon")
public class AnonUserController {
    private final SellerOfferService sellerOfferService;
    private final UserService userService;

    public AnonUserController(SellerOfferService sellerOfferService, UserService userService) {
        this.sellerOfferService = sellerOfferService;
        this.userService = userService;
    }

    @GetMapping("/sellers")
    public List<SellerDTO> findSellers(){
        return userService.findAllUsersByIsVerified(Role.SELLER, true);
    }

    @GetMapping("/sellers/game/{id}")
    public List<SellerDTO> findSellersByGameId(@PathVariable Integer id){
        return userService.findSellerByGameId(id);
    }

    @GetMapping("/sellers/{id}")
    public SellerDTO findSellerById(@PathVariable Integer id) {
        return userService.findSellerById(id);
    }

    @GetMapping("/sellers/rating")
    public List<ShowTopSellersResponseDTO> showSellersByRating(){
        return userService.sortSellersByRating();
    }

    @GetMapping("/sellers/ratingInRange")
    public List<ShowTopSellersResponseDTO> showSellersByRatingInRange(
            @RequestParam Float min,
            @RequestParam Float max
    ){
        return userService.sortSellersByRatingInRange(min, max);
    }

    @PostMapping("/sellers/comment")
    public ResponseEntity<String> addCommentAndRegisterSeller(@RequestBody AddCommentRequestDTO addCommentRequestDTO){
        userService.saveSellerByAnonUser(addCommentRequestDTO.sellerDTO(), addCommentRequestDTO.commentDTO());
        return ResponseEntity.ok("Comment added");
    }

    @PostMapping("/sellers/{id}/comment")
    public ResponseEntity<String> addCommentToExistingSeller(
            @PathVariable Integer id,
            @RequestBody CommentDTO commentDTO){
        userService.saveSellerByAnonUser(id, commentDTO);
        return ResponseEntity.ok("Comment added");
    }


}
