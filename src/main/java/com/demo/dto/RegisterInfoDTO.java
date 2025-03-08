package com.demo.dto;

import com.demo.entity.Game;

import java.util.List;

public record RegisterInfoDTO(
        String message,
        List<GameDTO> games
) {
}
