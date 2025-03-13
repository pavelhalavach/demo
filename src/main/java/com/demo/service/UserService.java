package com.demo.service;

import com.demo.dto.*;
import com.demo.entity.Role;

import java.util.List;

public interface UserService {
    void saveSeller(RegisterSellerRequestDTO registerSellerRequestDTO);
    void saveSellerByAnonUser(SellerDTO sellerDTO, CommentDTO commentDTO);

    //  ADDING COMMENT TO EXISTING SELLER
    void saveSellerByAnonUser(Integer id, CommentDTO commentDTO);

    void saveAdmin(RegisterAdminRequestDTO registerAdminRequestDTO);
    List<SellerDTO> findAllUsersByIsVerified(Role role, boolean isVerified);
    void reviewUser(Integer id, boolean decision);
    SellerDTO findSellerById(Integer id);
    List<ShowTopSellersResponseDTO> sortSellersByRating();
    List<SellerDTO> findSellerByGameId(Integer id);

    List<ShowTopSellersResponseDTO> sortSellersByRatingInRange(Float min, Float max);
}
