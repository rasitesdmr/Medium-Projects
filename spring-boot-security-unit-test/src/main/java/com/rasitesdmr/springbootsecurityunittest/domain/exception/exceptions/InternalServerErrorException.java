package com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RuntimeException {
    private final HttpStatus status;

    public InternalServerErrorException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getStatus() {
        return status;
    }
}