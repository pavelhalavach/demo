package com.demo.service;

import com.demo.dto.request.AddCommentRequestDTO;
import com.demo.dto.request.AddCommentWithRegRequestDTO;
import com.demo.dto.request.RegisterAdminRequestDTO;
import com.demo.dto.request.RegisterSellerRequestDTO;
import com.demo.dto.response.SellerDTO;
import com.demo.dto.response.ShowTopSellersResponseDTO;
import com.demo.entity.Role;

import java.util.List;

public interface UserService {
    void saveSeller(RegisterSellerRequestDTO registerSellerRequestDTO);
    void saveSellerByAnonUser(AddCommentWithRegRequestDTO addCommentWithRegRequestDTO);

    //  ADDING COMMENT TO EXISTING SELLER
    void saveSellerByAnonUser(Integer id, AddCommentRequestDTO addCommentRequestDTO);

    void saveAdmin(RegisterAdminRequestDTO registerAdminRequestDTO);
    List<SellerDTO> findAllUsersByIsVerified(Role role, boolean isVerified);
    ReviewStatus reviewUser(Integer id, boolean decision);
    SellerDTO findSellerById(Integer id);
    List<ShowTopSellersResponseDTO> sortSellersByRating();
    List<SellerDTO> findSellerByGameId(Integer id);

    List<ShowTopSellersResponseDTO> sortSellersByRatingInRange(Float min, Float max);
}
