package br.com.lasbr.smartbrain.controller;

import br.com.lasbr.smartbrain.dto.UserRequest;
import br.com.lasbr.smartbrain.dto.UserResponse;
import br.com.lasbr.smartbrain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

    @RestController
    @RequestMapping("api/v1/register")
    public class UserController {

        @Autowired
        private UserService service;

        @PostMapping
        public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest request) {
            UserResponse response = service.registerUser(request);
            return ResponseEntity.created(URI.create("/user" + response.id())).body(response);
        }
    }
