package br.com.lasbr.smartbrain.domain.dto;

import br.com.lasbr.smartbrain.domain.model.User;

import java.time.LocalDateTime;

    public record UserResponse(Long id, String name, String email, String password, LocalDateTime createdAt) {

        public UserResponse(User user) {
            this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCreatedAt());
        }
    }
