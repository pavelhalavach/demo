package com.demo.dto;

public record CommentDTO(
        Integer id,
        String message,
        Integer rating
) {
}
