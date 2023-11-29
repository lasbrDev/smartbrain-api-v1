package br.com.lasbr.smartbrain.service;

import br.com.lasbr.smartbrain.dto.UserRequest;
import br.com.lasbr.smartbrain.dto.UserResponse;
import br.com.lasbr.smartbrain.exception.RegistrationException;
import br.com.lasbr.smartbrain.model.User;
import br.com.lasbr.smartbrain.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

    @Service
    @Slf4j
    public class UserService {

        private final UserRepository repository;
        private final BCryptPasswordEncoder passwordEncoder;
        
        @Autowired
        public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
            this.repository = repository;
            this.passwordEncoder = passwordEncoder;
        }


        public UserResponse registerUser(UserRequest request) {
            try {
                validateRegistrationData(request);
                String hashedPassword = passwordEncoder.encode(request.password());
                User newUser = new User(request.name(), request.email(), hashedPassword);
                repository.save(newUser);

                log.info("Usuário registrado com sucesso: {}", newUser.getName());
                return new UserResponse(newUser);
            } catch (Exception e) {
                log.error("Erro ao registrar usuário", e);
                throw new RegistrationException("Erro ao registrar usuário", e);
            }
        }

        private void validateRegistrationData(UserRequest request) {
        }
    }
