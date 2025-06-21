package com.rasitesdmr.likeservice.domain.exception.exceptions;

import com.rasitesdmr.likeservice.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends BaseGraphQLException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}