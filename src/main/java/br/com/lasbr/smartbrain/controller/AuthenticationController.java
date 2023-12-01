package br.com.lasbr.smartbrain.controller;


import br.com.lasbr.smartbrain.domain.dto.UserLoginRequest;
import br.com.lasbr.smartbrain.domain.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/v1/login")
    public class AuthenticationController {

        private final AuthenticationManager manager;

        @Autowired
        public AuthenticationController(AuthenticationManager manager) {
            this.manager = manager;
        }

        @PostMapping
        public ResponseEntity<UserLoginRequest> login(@RequestBody @Valid UserLoginRequest request) {
            var token = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            var authentication = manager.authenticate(token);
            return ResponseEntity.ok().build();
        }
    }
