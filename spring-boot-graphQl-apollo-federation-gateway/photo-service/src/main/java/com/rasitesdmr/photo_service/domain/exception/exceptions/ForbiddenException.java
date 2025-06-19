package com.rasitesdmr.photo_service.domain.exception.exceptions;

import com.rasitesdmr.photo_service.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends BaseGraphQLException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}