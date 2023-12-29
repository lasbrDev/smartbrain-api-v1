package br.com.lasbr.smartbrain.infra.security;

import br.com.lasbr.smartbrain.domain.dto.UserLoginRequest;
import br.com.lasbr.smartbrain.domain.model.User;
import br.com.lasbr.smartbrain.infra.exception.JwtTokenGenerationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

    @Service
    public class TokenService {

        private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

        @Value("${api.security.token.secret}")
        private String secret;

        public String generateToken(UserLoginRequest request) {
            try {
                var algorithm = Algorithm.HMAC256(secret);
                String token = JWT.create()
                        .withIssuer("SmartBrain API")
                        .withSubject(request.email())
                        .withExpiresAt(expirationDate())
                        .sign(algorithm);
                logger.info("Token JWT gerado com sucesso para o usuário: {}", request.email());
                return token;
            } catch (JWTCreationException exception){
                logger.error("Erro ao gerar token JWT para o usuário: {}", request.email(), exception);
                throw new JwtTokenGenerationException("Erro ao gerar token JWT", exception);
            }
        }
        private Instant expirationDate() {
            return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
        }
    }
