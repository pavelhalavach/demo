package com.demo.dto.response;

public record ShowTopSellersResponseDTO (
        SellerDTO sellerDTO,
        Float avgRating
) {
}
