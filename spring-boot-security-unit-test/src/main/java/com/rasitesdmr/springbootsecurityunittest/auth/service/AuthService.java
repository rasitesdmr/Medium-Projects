package com.rasitesdmr.springbootsecurityunittest.auth.service;


import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthRegisterRequest;

public interface AuthService {
    String authRegister(AuthRegisterRequest authRegisterRequest);
    String authLogin(AuthLoginRequest authLoginRequest);
    String encodePassword(String password);
}
