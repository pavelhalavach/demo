package com.demo.dto;

import java.util.List;

public record RegisterSellerRequestDTO(
        String nickname,
        String firstName,
        String lastName,
        String email,
        String password,
        List<SellerOfferDTO> games
) {
}