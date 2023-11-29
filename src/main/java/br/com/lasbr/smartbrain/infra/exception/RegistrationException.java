package br.com.lasbr.smartbrain.infra.exception;

        public class RegistrationException extends RuntimeException {
            public RegistrationException(String message, Throwable cause) {
                super(message, cause);
            }

            public RegistrationException(String message) {
                super(message);
            }
        }
