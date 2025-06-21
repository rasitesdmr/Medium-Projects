package com.rasitesdmr.likeservice.domain.exception.exceptions;

import com.rasitesdmr.likeservice.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerErrorException extends BaseGraphQLException {
    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}