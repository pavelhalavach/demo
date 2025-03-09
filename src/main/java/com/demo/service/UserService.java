package com.demo.service;

import com.demo.dto.CommentDTO;
import com.demo.dto.RegisterAdminRequestDTO;
import com.demo.dto.RegisterSellerRequestDTO;
import com.demo.dto.SellerDTO;

import java.util.List;

public interface UserService {
    void saveSeller(RegisterSellerRequestDTO registerSellerRequestDTO);
    void saveSellerByAnonUser(SellerDTO sellerDTO, CommentDTO commentDTO);
    void saveAdmin(RegisterAdminRequestDTO registerAdminRequestDTO);
//    List<SellerDTO> findAllSellers();

    List<SellerDTO> findAllSellers(boolean isVerified);

    void reviewSeller(SellerDTO sellerDTO, boolean decision);

    void reviewComment(CommentDTO commentDTO, boolean decision);

    SellerDTO findSellerById(Integer id);

    SellerDTO findUserByEmail();

    void deleteUser(Integer id);
}
