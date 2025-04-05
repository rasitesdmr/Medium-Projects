package com.rasitesdmr.springboottransactionmanagement.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerErrorException extends RuntimeException {
    private final HttpStatus status;

    public InternalServerErrorException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}