package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.exceptions;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.BaseGraphQLException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException  extends BaseGraphQLException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}