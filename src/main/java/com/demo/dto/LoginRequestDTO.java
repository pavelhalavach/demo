package com.demo.dto;

public record LoginRequestDTO(
        String email,
        String password
    ) {
}
