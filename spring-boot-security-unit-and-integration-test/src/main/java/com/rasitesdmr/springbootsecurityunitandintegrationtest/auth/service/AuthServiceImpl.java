package com.rasitesdmr.springbootsecurityunitandintegrationtest.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.enums.RoleEnum;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.InvalidException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthRegisterRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt.JwtService;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.User;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AuthServiceImpl implements AuthService {

    private final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserService userService, ObjectMapper objectMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String authRegister(AuthRegisterRequest authRegisterRequest) {
        UserCreateRequest userCreateRequest = objectMapper.convertValue(authRegisterRequest, UserCreateRequest.class);
        String passwordEncode = encodePassword(userCreateRequest.getPassword());
        userCreateRequest.setRoleName(RoleEnum.ROLE_USER.name());
        userCreateRequest.setPassword(passwordEncode);
        userService.createUser(userCreateRequest);
        return "User Register Successful";
    }

    @Override
    public String authLogin(AuthLoginRequest authLoginRequest) {
        String requestUsername = authLoginRequest.getUsername();
        String requestPassword = authLoginRequest.getPassword();
        User currentUser = userService.getUserByUsername(requestUsername);

        String password = currentUser.getPassword();
        checkPassword(requestPassword, password);

        UUID id = currentUser.getId();
        String username = currentUser.getUserName();
        String roleName = currentUser.getRole().getRoleName();
        return jwtService.generateTokenWithClaims(id,username,roleName);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void checkPassword(String requestPassword, String userPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(requestPassword, userPassword);
        if (!isPasswordMatch) throw new InvalidException("Invalid Password");
    }
}
