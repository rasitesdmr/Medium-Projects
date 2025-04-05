package com.rasitesdmr.springboottransactionmanagement.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AlreadyAvailableException extends RuntimeException {

    private final HttpStatus status;

    public AlreadyAvailableException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }
}