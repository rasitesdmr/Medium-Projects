package com.rasitesdmr.likeservice.domain.exception.exceptions;

import com.rasitesdmr.likeservice.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends BaseGraphQLException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}