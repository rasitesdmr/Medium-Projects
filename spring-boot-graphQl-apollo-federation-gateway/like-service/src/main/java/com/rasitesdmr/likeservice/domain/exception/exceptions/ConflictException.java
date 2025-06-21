package com.rasitesdmr.likeservice.domain.exception.exceptions;

import com.rasitesdmr.likeservice.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends BaseGraphQLException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}