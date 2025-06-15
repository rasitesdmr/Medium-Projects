package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.exceptions;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends BaseGraphQLException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}