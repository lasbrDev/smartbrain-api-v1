package br.com.lasbr.smartbrain.domain.dto;

    public record UserLoginResponse(
            Long id,
            String name,
            String email) {
    }
