package com.demo.controller;


import com.demo.dto.CommentDTO;
import com.demo.dto.ReviewCommentRequestDTO;
import com.demo.dto.ReviewSellerRequestDTO;
import com.demo.dto.SellerDTO;
import com.demo.entity.User;
import com.demo.security.CustomUserDetails;
import com.demo.service.CommentService;
import com.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CommentService commentService;

    public AdminController(UserService userService, CommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/findSellers")
    public List<SellerDTO> findNonVerifiedSellers(){
        return userService.findAllSellers(false);
    }

    @GetMapping("/findComments")
    public List<CommentDTO> findNonVerifiedComments(){
        return commentService.findAllCommentsForAdmin();
    }

    @PostMapping("/reviewSeller")
    public String reviewSeller(@RequestBody ReviewSellerRequestDTO reviewSellerRequestDTO, Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User admin = customUserDetails.getUser();

        if (admin.isVerified()) {
            userService.reviewSeller(reviewSellerRequestDTO.sellerDTO(), reviewSellerRequestDTO.decision());
        } else {
            return "You are not verified to do that";
        }
        return "Successfully reviewed";
    }

    @PostMapping("/reviewComment")
    public String reviewComment(@RequestBody ReviewCommentRequestDTO reviewCommentRequestDTO, Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User admin = customUserDetails.getUser();

        System.out.println(admin.isVerified());
        if (admin.isVerified()) {
            userService.reviewComment(reviewCommentRequestDTO.commentDTO(), reviewCommentRequestDTO.decision());
        } else {
            return "You are not verified to do that";
        }
        return "Successfully reviewed";
    }
}
