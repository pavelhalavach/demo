package com.demo.dto;

import java.util.List;

public record SellerDTO(
    Integer id,
    String nickname,
    String firstName,
    String lastName,
    List<SellerOfferDTO> games,
    List<CommentDTO> comments
) {
}
