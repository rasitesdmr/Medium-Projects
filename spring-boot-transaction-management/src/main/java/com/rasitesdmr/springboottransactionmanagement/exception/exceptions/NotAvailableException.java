package com.rasitesdmr.springboottransactionmanagement.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAvailableException extends RuntimeException {
    private final HttpStatus status;

    public NotAvailableException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}