package com.demo.dto;

public record RegisterAdminRequestDTO (
    String nickname,
    String firstName,
    String lastName,
    String email,
    String password
) {
}
