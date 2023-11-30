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

        @GetMapping("/{id}")
        public ResponseEntity<UserResponse> getUserbyId(@PathVariable Long id) {
            try {
                UserResponse userProfile = service.getUserById(id);
                return ResponseEntity.ok(userProfile);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

//        @PostMapping
//        public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest loginRequest) {
//            try {
//                UserResponse userResponse = service.loginUser(loginRequest);
//                return new ResponseEntity<>(userResponse, HttpStatus.OK);
//            } catch (AuthenticationException e) {
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
    }
