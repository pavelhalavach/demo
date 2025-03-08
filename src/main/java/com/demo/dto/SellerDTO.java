package com.demo.dto;

import java.util.List;

public record SellerDTO(
    String nickname,
    String firstName,
    String lastName,
    List<SellerGameDTO> games,
    List<CommentDTO> comments
) {
}
