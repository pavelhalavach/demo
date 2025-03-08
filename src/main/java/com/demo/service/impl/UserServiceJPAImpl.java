package com.demo.service.impl;

import com.demo.dto.*;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.CommentService;
import com.demo.service.SellerGameService;
import com.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceJPAImpl implements UserService {
    private final UserRepository userRepository;
    private final SellerGameService sellerGameService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJPAImpl(
            UserRepository userRepository,
            SellerGameService sellerGameService, CommentService commentService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sellerGameService = sellerGameService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveSeller(RegisterSellerRequestDTO registerSellerRequestDTO) {
        User seller = new User();
        seller.setNickname(registerSellerRequestDTO.nickname());
        seller.setFirstName(registerSellerRequestDTO.firstName());
        seller.setLastName(registerSellerRequestDTO.lastName());
        seller.setEmail(registerSellerRequestDTO.email());
        seller.setPassword(passwordEncoder.encode(registerSellerRequestDTO.password()));
        seller.setRole(Role.valueOf("SELLER"));
        seller.setVerified(false);
        seller = userRepository.save(seller);

        for (SellerGameDTO sellerGameDTO : registerSellerRequestDTO.games()) {
            sellerGameService.saveSellerGame(seller, sellerGameDTO);
        }
    }

    @Override
    public void saveSellerByAnonUser(SellerDTO sellerDTO, CommentDTO commentDTO) {
        User seller = userRepository.findByNickname(sellerDTO.nickname());
        if (seller == null) {
            seller = new User();
            seller.setNickname(sellerDTO.nickname());
            seller.setFirstName(sellerDTO.firstName());
            seller.setLastName(sellerDTO.lastName());
            seller.setRole(Role.valueOf("SELLER"));
            seller.setVerified(false);
            seller = userRepository.save(seller);

            for (SellerGameDTO sellerGameDTO : sellerDTO.games()) {
                sellerGameService.saveSellerGame(seller, sellerGameDTO);
            }
        }

        commentService.saveComment(seller, commentDTO);
    }

    @Override
    public void saveAdmin(RegisterAdminRequestDTO registerAdminRequestDTO){

    }


    @Override
    public List<SellerDTO> findAllSellers() {
        List<SellerDTO> usersDTO = new ArrayList<>();
        for (User seller : userRepository.findAllByRole(Role.SELLER)){
            usersDTO.add(new SellerDTO(
                    seller.getNickname(),
                    seller.getFirstName(),
                    seller.getLastName(),
                    sellerGameService.findAllSellerGames(seller),
                    commentService.findAllCommentsBySeller(seller)
            ));
        }
        return usersDTO;
    }

    @Override
    public SellerDTO findSellerById(Integer id) {
        return userRepository.findByIdAndRole(id, Role.SELLER)
                .map(seller -> new SellerDTO(
                        seller.getNickname(),
                        seller.getFirstName(),
                        seller.getLastName(),
                        sellerGameService.findAllSellerGames(seller),
                        commentService.findAllCommentsBySeller(seller)
                )).orElseThrow(() -> new RuntimeException("Such seller is not found"));
    }

    @Override
    public SellerDTO findUserByEmail(){
        return null;
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
