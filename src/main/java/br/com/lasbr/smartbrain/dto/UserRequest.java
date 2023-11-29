package br.com.lasbr.smartbrain.dto;

    public record UserRequest(
            String name,
            String email,
            String password) {
    }
