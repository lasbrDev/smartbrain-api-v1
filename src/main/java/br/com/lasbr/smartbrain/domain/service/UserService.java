package br.com.lasbr.smartbrain.domain.service;

import br.com.lasbr.smartbrain.domain.repositories.UserRepository;
import br.com.lasbr.smartbrain.domain.dto.UserRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import br.com.lasbr.smartbrain.infra.exception.RegistrationException;
import br.com.lasbr.smartbrain.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        private void validateRegistrationData(UserRequest request) throws RegistrationException {
            String email = request.email();

            if (email == null || !isValidEmail(email)) {
                throw new RegistrationException("Endereço de e-mail inválido.");
            }
        }

        private boolean isValidEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
