package com.rasitesdmr.orchestratorservice.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAcceptableException extends RuntimeException {
    private final HttpStatus status;

    public NotAcceptableException(String message) {
        super(message);
        this.status = HttpStatus.NOT_ACCEPTABLE;
    }
}