package com.rasitesdmr.springbootsecurityunitandintegrationtest.auth.service;


import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthRegisterRequest;

public interface AuthService {
    String authRegister(AuthRegisterRequest authRegisterRequest);
    String authLogin(AuthLoginRequest authLoginRequest);
    String encodePassword(String password);
}
