package com.demo.dto;

import com.demo.dto.CommentDTO;
import com.demo.dto.SellerDTO;

public record AddCommentRequestDTO(
        SellerDTO sellerDTO,
        CommentDTO commentDTO
) {
}
