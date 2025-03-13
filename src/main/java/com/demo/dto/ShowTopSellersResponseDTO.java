package com.demo.dto;

public record ShowTopSellersResponseDTO (
        SellerDTO sellerDTO,
        Float avgRating
) {
}
