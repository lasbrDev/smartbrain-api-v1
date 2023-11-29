package br.com.lasbr.smartbrain.service;

import br.com.lasbr.smartbrain.dto.UserRequest;
import br.com.lasbr.smartbrain.dto.UserResponse;
import br.com.lasbr.smartbrain.model.User;
import br.com.lasbr.smartbrain.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

    @Service
    @Slf4j
    public class UserService {

        @Autowired
        private UserRepository repository;

        public UserResponse registerUser(UserRequest request) {
            try {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(request.password());
                User newUser = new User(request.name(), request.email(), hashedPassword);
                repository.save(newUser);

                log.info("Usuário registrado com sucesso: {}", newUser.getName());
                return new UserResponse(newUser.getName(), newUser.getEmail());
            } catch (Exception e) {
                log.error("Erro ao registrar usuário", e);
                throw new RuntimeException("Erro ao registrar usuário", e);
            }
        }
    }
