package br.com.lasbr.smartbrain.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

    public record UserRequest(

            @NotBlank(message = "Nome não pode estar em branco.")
            String name,
            @NotBlank(message = "Email não pode estar em branco.")
            @Email(message = "Email inválido")
            String email,
            @NotBlank(message = "Senha não pode estar em branco.")
            @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
            String password) {
    }
