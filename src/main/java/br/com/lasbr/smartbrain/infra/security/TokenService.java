package br.com.lasbr.smartbrain.infra.security;

import br.com.lasbr.smartbrain.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

    @Service
    public class TokenService {

        @Value("${api.security.token.secret}")
        private String secret;

        public String generateToken(User user) {
            try {
                var algorithm = Algorithm.HMAC256(secret);
                return JWT.create()
                        .withIssuer("SmartBrain API")
                        .withSubject(user.getEmail())
                        .withExpiresAt(expirationDate())
                        .sign(algorithm);
            } catch (JWTCreationException exception){
                throw new RuntimeException("Erro ao gerar token jwt", exception);
            }
        }

        private Instant expirationDate() {
            return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
        }
    }
