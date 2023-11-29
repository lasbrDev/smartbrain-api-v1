package br.com.lasbr.smartbrain.controller;

import br.com.lasbr.smartbrain.domain.dto.UserRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import br.com.lasbr.smartbrain.domain.service.UserService;
import br.com.lasbr.smartbrain.infra.exception.RegistrationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

    @RestController
    @RequestMapping("api/v1/register")
    @Slf4j
    public class UserController {

        private final UserService service;

        @Autowired
        public UserController(UserService service) {
            this.service = service;
        }

        @PostMapping
        public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest request) {
            try {
                UserResponse response = service.registerUser(request);
                return ResponseEntity.created(URI.create("/user" + response.id())).body(response);
            } catch (RegistrationException e) {
                log.error("Erro ao registrar usuário", e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } catch (Exception e) {
                log.error("Erro interno", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", e);
            }
        }
    }
