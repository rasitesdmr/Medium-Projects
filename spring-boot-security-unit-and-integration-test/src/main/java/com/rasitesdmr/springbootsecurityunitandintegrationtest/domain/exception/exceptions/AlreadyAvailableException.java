package com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyAvailableException extends RuntimeException {
    private final HttpStatus status;

    public AlreadyAvailableException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }

    public HttpStatus getStatus() {
        return status;
    }
}