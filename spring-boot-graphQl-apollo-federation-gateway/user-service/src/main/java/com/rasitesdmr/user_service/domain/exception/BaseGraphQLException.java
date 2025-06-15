package com.rasitesdmr.user_service.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseGraphQLException extends RuntimeException {
    private final HttpStatus status;

    protected BaseGraphQLException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
