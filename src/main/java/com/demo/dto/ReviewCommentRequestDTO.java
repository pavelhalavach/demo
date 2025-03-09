package com.demo.dto;

public record ReviewCommentRequestDTO(
        CommentDTO commentDTO,
        boolean decision
) {
}
