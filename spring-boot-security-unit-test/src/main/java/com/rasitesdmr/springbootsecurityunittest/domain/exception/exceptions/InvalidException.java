package com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidException extends RuntimeException {
    private final HttpStatus status;

    public InvalidException(String message) {
        super(message);
        this.status = HttpStatus.NOT_ACCEPTABLE;
    }

    public HttpStatus getStatus() {
        return status;
    }
}