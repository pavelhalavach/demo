package com.demo.dto;

public record RegisterAdminRequestDTO (
    String firstName,
    String lastName,
    String email,
    String password
) {
}
