package br.com.lasbr.smartbrain.infra.exception;

    public class ClarifaiServiceException extends RuntimeException {
        public ClarifaiServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
