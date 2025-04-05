package com.rasitesdmr.springboottransactionmanagement.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidException extends RuntimeException {
    private final HttpStatus status;

    public InvalidException(String message) {
        super(message);
        this.status = HttpStatus.NOT_ACCEPTABLE;
    }
}