package com.demo.service;

import com.demo.dto.request.*;
import com.demo.dto.response.SellerDTO;
import com.demo.dto.response.ShowTopSellersResponseDTO;
import com.demo.entity.Role;

import java.util.List;

public interface UserService {
    void registerSeller(RegisterSellerRequestDTO registerSellerRequestDTO);
    void registerSellerByAnonUser(AddCommentWithRegRequestDTO addCommentWithRegRequestDTO);
    void leaveCommentToSellerByAnonUser(Integer id, AddCommentRequestDTO addCommentRequestDTO);
    void registerAdmin(RegisterAdminRequestDTO registerAdminRequestDTO);

    void resetUserPassword(String email, String newPassword);

    List<SellerDTO> findAllUsersByIsVerified(Role role, boolean isVerified);
    ReviewStatus reviewUser(Integer id, boolean decision);
    SellerDTO findVerifiedSellerById(Integer id);
    List<ShowTopSellersResponseDTO> sortSellersByRating();
    List<SellerDTO> findSellerByGameId(Integer id);
    List<ShowTopSellersResponseDTO> sortSellersByRatingInRange(Float min, Float max);
    boolean checkRegistrationTokenDate(String token);
    void confirmRegistration(String token);
    void sendRestorationCodeToEmail(String email);
    boolean checkRestorationCode(String code, String email);
}
