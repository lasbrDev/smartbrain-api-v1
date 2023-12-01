package br.com.lasbr.smartbrain.controller;


import br.com.lasbr.smartbrain.domain.dto.UserLoginRequest;
import br.com.lasbr.smartbrain.domain.model.User;
import br.com.lasbr.smartbrain.infra.security.TokenJWTRequest;
import br.com.lasbr.smartbrain.infra.security.TokenService;
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
        private final TokenService service;

        @Autowired
        public AuthenticationController(AuthenticationManager manager, TokenService service) {
            this.manager = manager;
            this.service = service;
        }

        @PostMapping
        public ResponseEntity<TokenJWTRequest> login(@RequestBody @Valid UserLoginRequest request) {
            var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            var authentication = manager.authenticate(authenticationToken);
            var tokenJWT = service.generateToken((User) authentication.getPrincipal());
            return ResponseEntity.ok(new TokenJWTRequest(tokenJWT));
        }
    }
