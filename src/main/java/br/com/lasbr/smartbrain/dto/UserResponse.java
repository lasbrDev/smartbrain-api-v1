package br.com.lasbr.smartbrain.dto;

import br.com.lasbr.smartbrain.model.User;

    public record UserResponse(Long id, String name, String email) {

        public UserResponse(User user) {
            this(user.getId(), user.getName(), user.getEmail());
        }
    }
