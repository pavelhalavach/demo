package com.demo.dto.response;

import java.util.List;

public record RegisterInfoDTO(
        String message,
        List<GameDTO> games
) {
}
