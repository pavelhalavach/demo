package com.demo.service.impl;

import com.demo.dto.*;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.CommentService;
import com.demo.service.SellerOfferService;
import com.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceJPAImpl implements UserService {
    private final UserRepository userRepository;
    private final SellerOfferService sellerOfferService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJPAImpl(
            UserRepository userRepository,
            SellerOfferService sellerOfferService, CommentService commentService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sellerOfferService = sellerOfferService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveSeller(RegisterSellerRequestDTO registerSellerRequestDTO) {
        if (userRepository.findByNickname(registerSellerRequestDTO.nickname()) != null){
            throw new RuntimeException(
                    "Seller with such nickname " + registerSellerRequestDTO.nickname() + " already exists"
            );
        }
        User seller = new User();
        seller.setNickname(registerSellerRequestDTO.nickname());
        seller.setFirstName(registerSellerRequestDTO.firstName());
        seller.setLastName(registerSellerRequestDTO.lastName());
        seller.setEmail(registerSellerRequestDTO.email());
        seller.setPassword(passwordEncoder.encode(registerSellerRequestDTO.password()));
        seller.setRole(Role.valueOf("SELLER"));
        seller.setVerified(false);
        seller = userRepository.save(seller);

        for (SellerOfferDTO sellerOfferDTO : registerSellerRequestDTO.games()) {
            sellerOfferService.saveSellerOffer(seller, sellerOfferDTO);
        }
    }

//  REGISTRATION SELLER PROFILE + ADDING COMMENT
    @Override
    public void saveSellerByAnonUser(SellerDTO sellerDTO, CommentDTO commentDTO) {
        if (userRepository.findByNickname(sellerDTO.nickname()) != null){
            throw new RuntimeException(
                    "Seller with such nickname " + sellerDTO.nickname() + " already exists"
            );
        }
        User seller = new User();
        seller.setNickname(sellerDTO.nickname());
        seller.setFirstName(sellerDTO.firstName());
        seller.setLastName(sellerDTO.lastName());
        seller.setRole(Role.valueOf("SELLER"));
        seller.setVerified(false);
        seller = userRepository.save(seller);
        for (SellerOfferDTO sellerOfferDTO : sellerDTO.games()) {
            sellerOfferService.saveSellerOffer(seller, sellerOfferDTO);
        }

        commentService.saveCommentByAnonUser(seller, commentDTO);
    }

//  ADDING COMMENT TO EXISTING SELLER
    @Override
    public void saveSellerByAnonUser(Integer id, CommentDTO commentDTO) {
        User seller = userRepository.findByIdAndRole(id, Role.SELLER)
                .orElseThrow(() -> new RuntimeException("Seller was not found with id " + id));
        commentService.saveCommentByAnonUser(seller, commentDTO);
    }

    @Override
    public void saveAdmin(RegisterAdminRequestDTO registerAdminRequestDTO){
        User admin = new User();
        admin.setNickname(registerAdminRequestDTO.nickname());
        admin.setFirstName(registerAdminRequestDTO.firstName());
        admin.setLastName(registerAdminRequestDTO.lastName());
        admin.setEmail(registerAdminRequestDTO.email());
        admin.setPassword(passwordEncoder.encode(registerAdminRequestDTO.password()));
        admin.setRole(Role.valueOf("ADMIN"));
        admin.setVerified(false);
        userRepository.save(admin);
    }


    @Override
    public List<SellerDTO> findAllUsersByIsVerified(Role role, boolean isVerified) {
        List<SellerDTO> usersDTO = new ArrayList<>();
        for (User user : userRepository.findAllByRoleAndIsVerified(role, isVerified)) {

            List<SellerOfferDTO> sellerOfferDTOs = null;
            List<CommentDTO> commentDTOs = null;
            if (isVerified) {
                sellerOfferDTOs = sellerOfferService.findAllVerifiedSellerOffers(user);
                commentDTOs = commentService.findAllVerifiedCommentsBySeller(user);
            } else {
                sellerOfferDTOs = sellerOfferService.findAllSellerOffersDTO(user);
                commentDTOs = commentService.findAllCommentsBySeller(user);
            }
            usersDTO.add(new SellerDTO(
                    user.getId(),
                    user.getNickname(),
                    user.getFirstName(),
                    user.getLastName(),
                    sellerOfferDTOs,
                    commentDTOs
            ));
        }
        return usersDTO;
    }

    @Override
    public void reviewUser(Integer id, boolean decision){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller was not found with id " + id));
        if (decision) {
            user.setVerified(true);
            userRepository.save(user);
        } else {
            sellerOfferService.deleteAllByUserId(id);
            userRepository.delete(user);
        }
    }

    @Override
    public SellerDTO findSellerById(Integer id) {
        return userRepository.findByIdAndRole(id, Role.SELLER)
                .map(seller -> new SellerDTO(
                        seller.getId(),
                        seller.getNickname(),
                        seller.getFirstName(),
                        seller.getLastName(),
                        sellerOfferService.findAllVerifiedSellerOffers(seller),
                        commentService.findAllVerifiedCommentsBySeller(seller)
                )).orElseThrow(() -> new RuntimeException("Such seller is not found"));
    }

    @Override
    public List<ShowTopSellersResponseDTO> sortSellersByRating() {
        List<ShowTopSellersResponseDTO> sellersByRating = new ArrayList<>();
        List<User> sellers = userRepository.findAllByRole(Role.SELLER);
        Float avgRating;
        SellerDTO sellerDTO;
        for (User seller : sellers){
            sellerDTO = new SellerDTO(seller.getId(),
                    seller.getNickname(),
                    seller.getFirstName(),
                    seller.getLastName(),
                    sellerOfferService.findAllVerifiedSellerOffers(seller),
                    commentService.findAllVerifiedCommentsBySeller(seller));

            avgRating = commentService.findAverageRatingBySellerId(seller.getId());
            if (avgRating == null) {
                avgRating = 0F;
            }
            avgRating = Math.round(avgRating * 10) / 10.0f;
            sellersByRating.add(new ShowTopSellersResponseDTO(sellerDTO, avgRating));
        }

        sellersByRating.sort(Comparator.comparing(ShowTopSellersResponseDTO::avgRating).reversed());
        return sellersByRating;
    }

    @Override
    public List<ShowTopSellersResponseDTO> sortSellersByRatingInRange(Float min, Float max) {
        return sortSellersByRating()
                .stream()
                .filter(seller -> (seller.avgRating() >= min && seller.avgRating() <= max))
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerDTO> findSellerByGameId(Integer id) {
        return userRepository.findAllByRole(Role.SELLER)
                .stream()
                .filter(seller -> sellerOfferService.isVerifiedSellerOfferExistByGameId(seller, id))
                .map(seller -> new SellerDTO(
                        seller.getId(),
                        seller.getNickname(),
                        seller.getFirstName(),
                        seller.getLastName(),
                        sellerOfferService.findAllVerifiedSellerOffers(seller),
                        commentService.findAllVerifiedCommentsBySeller(seller)))
                .collect(Collectors.toList());
    }

}
