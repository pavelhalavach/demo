package com.demo.dto;

import java.util.List;

public record RegisterSellerRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String role, //ADD EXCEPTION IF role IS NOT ADMIN OR SELLER
        List<SellerGameDTO> games
) {
}