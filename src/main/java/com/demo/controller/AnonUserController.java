package com.demo.controller;

import com.demo.dto.request.AddCommentRequestDTO;
import com.demo.dto.request.AddCommentWithRegRequestDTO;
import com.demo.dto.response.CommentDTO;
import com.demo.dto.response.SellerDTO;
import com.demo.dto.response.ShowTopSellersResponseDTO;
import com.demo.entity.Role;
import com.demo.service.CommentService;
import com.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anon")
public class AnonUserController {
    private final UserService userService;
    private final CommentService commentService;

    public AnonUserController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
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
        return userService.findVerifiedSellerById(id);
    }

    @GetMapping("/sellers/{id}/comments")
    public List<CommentDTO> findCommentsBySellerId(@PathVariable Integer id){
        return commentService.findAllCommentsBySellerId(id);
    }
    @GetMapping("/sellers/{seller_id}/comments/{comment_id}")
    public CommentDTO findCommentBySellerId(
            @PathVariable(name = "seller_id") Integer sellerId,
            @PathVariable(name = "comment_id") Integer commentId){
        return commentService.findCommentByIdAndSellerId(sellerId, commentId);
    }

    @GetMapping("/sellers/rating")
    public List<ShowTopSellersResponseDTO> showSellersByRating(){
        return userService.sortSellersByRating();
    }

    @GetMapping("/sellers/rating_in_range")
    public List<ShowTopSellersResponseDTO> showSellersByRatingInRange(
            @RequestParam Float min,
            @RequestParam Float max
    ){
        return userService.sortSellersByRatingInRange(min, max);
    }

    @PostMapping("/sellers/comment")
    public ResponseEntity<String> addCommentAndRegisterSeller(
            @RequestBody AddCommentWithRegRequestDTO addCommentWithRegRequestDTO){
        userService.registerSellerByAnonUser(addCommentWithRegRequestDTO);
        return ResponseEntity.ok("Comment added");
    }

    @PostMapping("/sellers/{id}/comment")
    public ResponseEntity<String> addCommentToExistingSeller(
            @PathVariable Integer id,
            @RequestBody AddCommentRequestDTO addCommentRequestDTO){
        userService.leaveCommentToSellerByAnonUser(id, addCommentRequestDTO);
        return ResponseEntity.ok("Comment added");
    }


}
