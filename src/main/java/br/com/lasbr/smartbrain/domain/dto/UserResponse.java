package br.com.lasbr.smartbrain.domain.dto;

import br.com.lasbr.smartbrain.domain.model.User;

public record UserResponse(Long id, String name, String email) {

        public UserResponse(User user) {
            this(user.getId(), user.getName(), user.getEmail());
        }
    }
