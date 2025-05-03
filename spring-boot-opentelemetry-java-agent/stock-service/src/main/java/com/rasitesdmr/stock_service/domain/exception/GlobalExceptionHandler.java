package com.rasitesdmr.stock_service.domain.exception;

import com.rasitesdmr.stock_service.domain.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class) // Hatalı İstek : 400
    public ResponseEntity<Object> handel(BadRequestException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class) // Yetkisiz : 401
    public ResponseEntity<Object> handel(UnauthorizedException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class) // Yasak  : 403
    public ResponseEntity<Object> handel(ForbiddenException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class) // Bulunamadı : 404
    public ResponseEntity<Object> handel(NotFoundException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());

    }

    @ExceptionHandler(NotAcceptableException.class) // Kabul Edilemez : 406
    public ResponseEntity<Object> handel(NotAcceptableException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());

    }

    @ExceptionHandler(ConflictException.class)  // Zaten Mevcut : 409
    public ResponseEntity<Object> handel(ConflictException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());

    }

    @ExceptionHandler(InternalServerErrorException.class) // Sunucu hatası : 500
    public ResponseEntity<Object> handel(InternalServerErrorException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorsMap(errors));
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
