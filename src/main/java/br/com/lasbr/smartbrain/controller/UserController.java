package br.com.lasbr.smartbrain.controller;

import br.com.lasbr.smartbrain.domain.dto.UserLoginRequest;
import br.com.lasbr.smartbrain.domain.dto.UserRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import br.com.lasbr.smartbrain.domain.service.UserService;
import br.com.lasbr.smartbrain.infra.exception.RegistrationException;
import br.com.lasbr.smartbrain.infra.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.AuthenticationException;
import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;

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
        public ResponseEntity<UserResponse> registerUser(
                @RequestBody @Valid UserRequest request, UriComponentsBuilder uriBuilder) {
            try {
                UserResponse response = service.registerUser(request);
                URI location = uriBuilder.path("/api/v1/register/{id}").buildAndExpand(response.id()).toUri();
                return ResponseEntity.created(location).body(response);
            } catch (RegistrationException e) {
                log.error("Erro ao registrar usuário", e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } catch (Exception e) {
                log.error("Erro interno", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", e);
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getUserbyId(@PathVariable Long id) {
            try {
                UserResponse userProfile = service.getUserById(id);
                return ResponseEntity.ok(userProfile);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao obter usuário por ID");
            }
        }
    }
