package br.com.lasbr.smartbrain.controller;

import br.com.lasbr.smartbrain.domain.dto.UserLoginRequest;
import br.com.lasbr.smartbrain.domain.dto.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.lasbr.smartbrain.domain.dto.UserRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import br.com.lasbr.smartbrain.domain.service.UserService;
import jakarta.validation.Valid;


import java.net.URI;

    @RestController
    @RequestMapping("api/v1/register")
    public class UserController {

        private final UserService service;

        @Autowired
        public UserController(UserService service) {
            this.service = service;
        }

        @PostMapping("/register")
        public ResponseEntity<UserResponse> registerUser(
                @RequestBody @Valid UserRequest request, UriComponentsBuilder uriBuilder) {
            UserResponse userResponse = service.registerUser(request);
            var uri = uriBuilder.path("/api/v1/register/{id}").buildAndExpand(userResponse).toUri();
            return ResponseEntity.created(uri).body(userResponse);
        }
    }
