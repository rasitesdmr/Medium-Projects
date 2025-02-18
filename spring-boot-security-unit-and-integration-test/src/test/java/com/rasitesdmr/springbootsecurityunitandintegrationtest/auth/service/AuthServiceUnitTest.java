package com.rasitesdmr.springbootsecurityunitandintegrationtest.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.enums.RoleEnum;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.InvalidException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthRegisterRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt.JwtService;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.User;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private static final String FIRST_NAME = "Raşit";
    private static final String LAST_NAME = "Eşdemir";
    private static final String USERNAME = "rasitesdmr";
    private static final String EMAIL = "rasitesdemir@gmail.com";
    private static final String PASSWORD = "123";
    private static final String ENCODE_PASSWORD = "$2a$10$/wXgZ.C2WIMG9lvy5Kiv/.bblSmmBeQrP1lNHv/DpzYbNmnz6J2k.";
    private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiMzJjMmI0YTUtZTEzMS00ZmY1LTg4NTctZDkwZmZiOTQ1ZWVkIiwiaWF0IjoxNzM2MTczNDY0LCJleHAiOjE3MzYyNTk4NjR9.jKzM-47soqro1gvqVESGMFt6LmZzaNbNHuA0V8qTej0";

    private final String exceptedPassword = ENCODE_PASSWORD;


    @Test
    @DisplayName("Method authRegister should convert authRegisterRequest To userCreateRequest when valid is provided")
    void authRegister_shouldConvertAuthRegisterRequestToUserCreateRequest_whenValidRequestIsProvided() {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        UserCreateRequest exceptedRequest = new UserCreateRequest("Raşit", "Eşdemir", "rasitesdmr", "rasitesdemir@gmail.com", "123", null);

        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(exceptedRequest);

        authService.authRegister(request);

        Mockito.verify(objectMapper).convertValue(request, UserCreateRequest.class);
        Mockito.verifyNoMoreInteractions(objectMapper);

        assertEquals(exceptedRequest.getFirstName(), request.getFirstName());
        assertEquals(exceptedRequest.getLastName(), request.getLastName());
        assertEquals(exceptedRequest.getUserName(), request.getUserName());
        assertEquals(exceptedRequest.getEmail(), request.getEmail());
    }

    @Test
    @DisplayName("Method authRegister should encode password when valid password is provided")
    void authRegister_shouldEncodePassword_whenValidPasswordIsProvided() {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        UserCreateRequest exceptedRequest = new UserCreateRequest("Raşit", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "123", null);

        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(exceptedRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(exceptedPassword);

        authService.authRegister(request);

        Mockito.verify(passwordEncoder).encode(request.getPassword());
        Mockito.verifyNoMoreInteractions(passwordEncoder);
        assertEquals(exceptedPassword, exceptedRequest.getPassword());
    }

    @Test
    @DisplayName("Method authRegister should set correct role name and encode password when default role is assigned")
    void authRegister_shouldSetCorrectRoleNameAndEncodePassword_whenDefaultRoleIsAssigned() {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        UserCreateRequest exceptedRequest = new UserCreateRequest("Raşit", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "123", null);

        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(exceptedRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(exceptedPassword);

        authService.authRegister(request);

        assertEquals(RoleEnum.ROLE_USER.name(), exceptedRequest.getRoleName());
        assertEquals(exceptedPassword, exceptedRequest.getPassword());
    }

    @Test
    @DisplayName("Method authRegister should call createUser when service is invoked")
    void authRegister_shouldCallCreateUser_whenUserServiceIsInvoked() {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        UserCreateRequest exceptedRequest = new UserCreateRequest("Raşit", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "123", null);

        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(exceptedRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(exceptedPassword);

        authService.authRegister(request);

        Mockito.verify(userService).createUser(exceptedRequest);
    }

    @Test
    @DisplayName("Method authRegister should return success message when all steps are successful")
    void authRegister_shouldReturnSuccessMessage_whenAllStepsAreSuccessful() {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        UserCreateRequest exceptedRequest = new UserCreateRequest("Raşit", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "123", null);
        final String exceptedResult = "User Register Successful";

        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(exceptedRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(exceptedPassword);

        String result = authService.authRegister(request);

        assertEquals(exceptedResult, result);
    }

    @Test
    @DisplayName("Method authLogin should throw not found exception when user does not exist")
    void authLogin_shouldThrowNotFoundException_whenUserDoesNotExist() {
        AuthLoginRequest request = new AuthLoginRequest(USERNAME, PASSWORD);
        String exceptedResponse = "User with the username rasitesdmr not found.";

        Mockito.when(userService.getUserByUsername(request.getUsername())).thenThrow(new NotAvailableException(exceptedResponse));

        NotAvailableException exception = assertThrows(NotAvailableException.class, () -> {
            authService.authLogin(request);
        });

        assertEquals(exceptedResponse, exception.getMessage());
        Mockito.verify(userService).getUserByUsername(request.getUsername());
    }

    @Test
    @DisplayName("Method authLogin should throw invalid exception when password is incorrect")
    void authLogin_shouldThrowInvalidException_whenPasswordIsIncorrect() {
        AuthLoginRequest request = new AuthLoginRequest(USERNAME, "222");
        String exceptedResponse = "Invalid Password";
        User exceptedUser = new User(UUID.randomUUID(), FIRST_NAME, LAST_NAME, USERNAME, EMAIL, ENCODE_PASSWORD, ENCODE_PASSWORD, null, null, null);

        Mockito.when(userService.getUserByUsername(request.getUsername())).thenReturn(exceptedUser);
        Mockito.when(passwordEncoder.matches(request.getPassword(), exceptedUser.getPassword())).thenReturn(false);

        InvalidException exception = Assertions.assertThrows(InvalidException.class, () -> {
            authService.authLogin(request);
        });

        assertEquals(exceptedResponse, exception.getMessage());
        Mockito.verify(passwordEncoder).matches(request.getPassword(), exceptedUser.getPassword());
    }

    @Test
    @DisplayName("Method authLogin should call generate token with correct claims when password matchers.")
    void authLogin_shouldCallGenerateTokenWithCorrectClaims_whenPasswordMatchers(){
        AuthLoginRequest request = new AuthLoginRequest(USERNAME, PASSWORD);
        User exceptedUser = new User(UUID.randomUUID(), FIRST_NAME, LAST_NAME, USERNAME, EMAIL, ENCODE_PASSWORD, ENCODE_PASSWORD, null, null, new Role(UUID.randomUUID(), RoleEnum.ROLE_USER.name(), null, null));

        Mockito.when(userService.getUserByUsername(request.getUsername())).thenReturn(exceptedUser);
        Mockito.when(passwordEncoder.matches(request.getPassword(), exceptedUser.getPassword())).thenReturn(true);

        authService.authLogin(request);

        Mockito.verify(jwtService).generateTokenWithClaims(exceptedUser.getId(), exceptedUser.getUserName(),exceptedUser.getRole().getRoleName());
    }

    @Test
    void authLogin_shouldReturnJwtToken_whenCredentialsAreValid(){
        AuthLoginRequest request = new AuthLoginRequest(USERNAME, PASSWORD);
        User exceptedUser = new User(UUID.randomUUID(), FIRST_NAME, LAST_NAME, USERNAME, EMAIL, ENCODE_PASSWORD, ENCODE_PASSWORD, null, null, new Role(UUID.randomUUID(), RoleEnum.ROLE_USER.name(), null, null));

        Mockito.when(userService.getUserByUsername(request.getUsername())).thenReturn(exceptedUser);
        Mockito.when(passwordEncoder.matches(request.getPassword(), exceptedUser.getPassword())).thenReturn(true);
        Mockito.when(jwtService.generateTokenWithClaims(exceptedUser.getId(), exceptedUser.getUserName(), exceptedUser.getRole().getRoleName())).thenReturn(JWT);

       String result =  authService.authLogin(request);

        assertEquals(JWT, result);

        Mockito.verify(userService).getUserByUsername(request.getUsername());
        Mockito.verify(passwordEncoder).matches(request.getPassword(), exceptedUser.getPassword());
        Mockito.verify(jwtService).generateTokenWithClaims(exceptedUser.getId(), exceptedUser.getUserName(),exceptedUser.getRole().getRoleName());
    }

}