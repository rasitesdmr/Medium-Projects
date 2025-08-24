package com.rasitesdmr.orchestratorservice.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final HttpStatus status;

    public UnauthorizedException(String message) {
        super(message);
        status = HttpStatus.UNAUTHORIZED;
    }
}