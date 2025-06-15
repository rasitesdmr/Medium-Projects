package com.rasitesdmr.user_service.domain.exception.exceptions;

import com.rasitesdmr.user_service.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotAcceptableException extends BaseGraphQLException { ;
    public NotAcceptableException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}