package com.rasitesdmr.photo_service.domain.exception.exceptions;

import com.rasitesdmr.photo_service.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException  extends BaseGraphQLException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}