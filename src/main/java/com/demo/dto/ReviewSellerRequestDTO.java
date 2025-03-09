package com.demo.dto;

public record ReviewSellerRequestDTO(
        SellerDTO sellerDTO,
        boolean decision
) {
}
