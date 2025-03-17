package com.demo.service.impl;

import com.demo.dto.request.*;
import com.demo.dto.response.CommentDTO;
import com.demo.dto.response.SellerDTO;
import com.demo.dto.response.SellerOfferDTO;
import com.demo.dto.response.ShowTopSellersResponseDTO;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.exception.UserNotFoundException;
import com.demo.repository.UserRepository;
import com.demo.service.CommentService;
import com.demo.service.ReviewStatus;
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
        User seller = new User();
        seller.setNickname(registerSellerRequestDTO.nickname());
        seller.setFirstName(registerSellerRequestDTO.firstName());
        seller.setLastName(registerSellerRequestDTO.lastName());
        seller.setEmail(registerSellerRequestDTO.email());
        seller.setPassword(passwordEncoder.encode(registerSellerRequestDTO.password()));
        seller.setRole(Role.valueOf("SELLER"));
        seller.setVerified(false);
        seller = userRepository.save(seller);

        for (RegisterSellerOfferDTO registerSellerOfferDTO : registerSellerRequestDTO.games()) {
            sellerOfferService.saveSellerOffer(seller, registerSellerOfferDTO);
        }
    }

//  REGISTRATION SELLER PROFILE + ADDING COMMENT
    @Override
    public void saveSellerByAnonUser(AddCommentWithRegRequestDTO addCommentWithRegRequestDTO) {
        User seller = new User();
        seller.setNickname(addCommentWithRegRequestDTO.sellerNickname());
        seller.setFirstName(addCommentWithRegRequestDTO.sellerFirstName());
        seller.setLastName(addCommentWithRegRequestDTO.sellerLastName());
        seller.setRole(Role.valueOf("SELLER"));
        seller.setVerified(false);
        seller = userRepository.save(seller);
        for (String gameName : addCommentWithRegRequestDTO.sellerGames()) {
            sellerOfferService.saveSellerOffer(seller, new RegisterSellerOfferDTO(gameName, null));
        }

        commentService.saveCommentByAnonUser(seller,
                addCommentWithRegRequestDTO.commentMessage(),
                addCommentWithRegRequestDTO.commentRating()
        );
    }

//  ADDING COMMENT TO EXISTING SELLER
    @Override
    public void saveSellerByAnonUser(Integer id, AddCommentRequestDTO addCommentRequestDTO) {
        User seller = userRepository.findByIdAndRole(id, Role.SELLER)
                .orElseThrow(() -> new UserNotFoundException(id));
        commentService.saveCommentByAnonUser(
                seller,
                addCommentRequestDTO.commentMessage(),
                addCommentRequestDTO.commentRating()
        );
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
                commentDTOs = commentService.findAllCommentsBySellerId(user.getId());
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
    public ReviewStatus reviewUser(Integer id, boolean decision){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (user.isVerified()){
           return ReviewStatus.ALREADY_VERIFIED;
        }
        if (decision) {
            user.setVerified(true);
            userRepository.save(user);
        } else {
            userRepository.delete(user);
        }
        return ReviewStatus.SUCCESS;
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
                )).orElseThrow(() -> new UserNotFoundException(id));
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
