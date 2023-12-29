package br.com.lasbr.smartbrain.domain.dto;

import java.time.LocalDateTime;

    public record UserResponse(Long id, String name, String email, String password, LocalDateTime createdAt) {
    }
