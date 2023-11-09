package br.com.redbag.api.exceptions;

public class ForbiddenUserRequestException extends RuntimeException {
    public ForbiddenUserRequestException(String message) {
        super(message);
    }
}
