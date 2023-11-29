package br.com.lasbr.smartbrain.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

    public record UserLoginRequest(

            @NotBlank(message = "Email não pode estar em branco.")
            @Email(message = "Email inválido.")
            String email,
            @NotBlank(message = "Senha não pode estar em branco.")
            String password) {
    }