package br.com.lasbr.smartbrain.infra.exception;

import com.auth0.jwt.exceptions.JWTCreationException;

    public class JwtTokenGenerationException extends RuntimeException {
        public JwtTokenGenerationException(String message, JWTCreationException exception) {
            super(message, exception);
        }
    }
