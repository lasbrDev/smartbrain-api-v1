package br.com.lasbr.smartbrain.domain.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.lasbr.smartbrain.domain.dto.UserLoginRequest;
import br.com.lasbr.smartbrain.domain.dto.UserLoginResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lasbr.smartbrain.domain.dto.UserRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import br.com.lasbr.smartbrain.domain.model.User;
import br.com.lasbr.smartbrain.domain.repositories.UserRepository;
import br.com.lasbr.smartbrain.infra.exception.RegistrationException;
import org.springframework.transaction.annotation.Transactional;

    @Service
    public class UserService {

        private final UserRepository repository;
        private final BCryptPasswordEncoder passwordEncoder;
        private final Logger logger = LoggerFactory.getLogger(UserService.class);

        @Autowired
        public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
            this.repository = repository;
            this.passwordEncoder = passwordEncoder;
        }

        @Transactional
        public UserResponse registerUser(UserRequest request) {
            try {
                if (repository.existsByEmail(request.email())) {
                    throw new IllegalArgumentException("E-mail já está em uso.");
                }
                User user = new User(request.name(), request.email(), passwordEncoder.encode(request.password()));
                User savedUser = repository.save(user);
                logger.info("Usuário registrado com sucesso: {}", savedUser.getEmail());
                return new UserResponse(savedUser.getId(), savedUser.getName(),
                        savedUser.getEmail(), savedUser.getPassword(), savedUser.getCreatedAt());
            } catch (DataIntegrityViolationException e) {
                logger.error("Erro ao registrar usuário", e);
            }
            Throwable e = null;
            throw new RegistrationException("Erro ao registrar usuário", e);
        }
    }
