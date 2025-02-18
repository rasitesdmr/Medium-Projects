package com.rasitesdmr.springbootsecurityunittest.auth.controller;

import com.rasitesdmr.springbootsecurityunittest.auth.service.AuthService;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auths")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    @Operation(summary = "Auth User Register Endpoint")
    public ResponseEntity<String> authRegister(@Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
        return new ResponseEntity<>(authService.authRegister(authRegisterRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    @Operation(summary = "Auth User Login Endpoint")
    public ResponseEntity<String> authLogin(@RequestBody AuthLoginRequest authLoginRequest) {
        return new ResponseEntity<>(authService.authLogin(authLoginRequest), HttpStatus.CREATED);
    }
}
