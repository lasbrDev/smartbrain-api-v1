package br.com.lasbr.smartbrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lasbr.smartbrain.domain.dto.UserRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import br.com.lasbr.smartbrain.domain.service.UserService;
import br.com.lasbr.smartbrain.infra.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

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
            UserResponse userResponse = service.registerUser(request);
            var uri = uriBuilder.path("/api/v1/register/{id}").buildAndExpand(userResponse).toUri();
            return ResponseEntity.created(uri).body(userResponse);
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getUserbyId(@PathVariable Long id) {
            try {
                UserResponse userProfile = service.getUserById(id);
                return ResponseEntity.ok(userProfile);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        "Erro interno ao obter usuário por ID");
            }
        }
    }
