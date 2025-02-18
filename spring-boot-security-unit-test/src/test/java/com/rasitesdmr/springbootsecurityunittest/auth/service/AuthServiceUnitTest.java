package com.rasitesdmr.springbootsecurityunittest.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.AlreadyAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.InvalidException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthRegisterRequest;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunittest.security.jwt.JwtService;
import com.rasitesdmr.springbootsecurityunittest.user.User;
import com.rasitesdmr.springbootsecurityunittest.user.service.UserService;
import com.rasitesdmr.springbootsecurityunittest.data.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("[Method authRegister] Given existing username When registering user Then should throw already available exception")
    void givenExistingUsername_whenRegisteringUser_thenShouldThrowAlreadyAvailableException() {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        UserCreateRequest convertRequest = TestData.userCreateRequest;
        final String expectedEncodePassword = TestData.encodePassword;
        final String expectedErrorMessage = String.format("User with the username %s already available.", request.getUserName());
        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(convertRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(expectedEncodePassword);
        Mockito.doThrow(new AlreadyAvailableException(expectedErrorMessage)).when(userService).createUser(convertRequest);

        // Act
        AlreadyAvailableException exception = assertThrows(AlreadyAvailableException.class, () ->
                authService.authRegister(request)
        );

        // Assert
        final String resultExceptionMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultExceptionMessage);
        Mockito.verify(objectMapper).convertValue(request, UserCreateRequest.class);
        Mockito.verify(passwordEncoder).encode(request.getPassword());
        Mockito.verify(userService).createUser(convertRequest);
    }

    @Test
    @DisplayName("[Method authRegister] Given invalid role name When registering user Then should throw not available exception")
    void givenInvalidRoleName_whenRegisteringUser_thenShouldThrowNotAvailableException() {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        UserCreateRequest convertRequest = TestData.userCreateRequest;
        final String expectedEncodePassword = TestData.encodePassword;
        final String expectedErrorMessage = String.format("Role with the role name %s not found.", "ROLE_USER");
        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(convertRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(expectedEncodePassword);
        Mockito.doThrow(new NotAvailableException(expectedErrorMessage)).when(userService).createUser(convertRequest);

        // Act
        NotAvailableException exception = assertThrows(NotAvailableException.class, () ->
                authService.authRegister(request)
        );

        // Assert
        final String resultExceptionMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultExceptionMessage);
        Mockito.verify(objectMapper).convertValue(request, UserCreateRequest.class);
        Mockito.verify(passwordEncoder).encode(request.getPassword());
        Mockito.verify(userService).createUser(convertRequest);
    }

    @Test
    @DisplayName("[Method authRegister] Given database error When registering user Then should throw internal server error exception")
    void givenDatabaseError_whenRegisteringUser_thenShouldThrowInternalServerErrorException() {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        UserCreateRequest convertRequest = TestData.userCreateRequest;
        final String expectedEncodePassword = TestData.encodePassword;
        final String expectedErrorMessage = String.format("User Not Saved : %s", "Database Error");
        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(convertRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(expectedEncodePassword);
        Mockito.doThrow(new InternalServerErrorException(expectedErrorMessage)).when(userService).createUser(convertRequest);

        // Act
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () ->
                authService.authRegister(request)
        );

        // Assert
        final String resultExceptionMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultExceptionMessage);
        Mockito.verify(objectMapper).convertValue(request, UserCreateRequest.class);
        Mockito.verify(passwordEncoder).encode(request.getPassword());
        Mockito.verify(userService).createUser(convertRequest);
    }

    @Test
    @DisplayName("[Method authRegister] Given all steps successful When register user Then should return success message")
    void givenAllStepsSuccessful_whenRegisteringUser_thenShouldReturnSuccessMessage() {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        UserCreateRequest convertRequest = TestData.userCreateRequest;
        final String expectedEncodePassword = TestData.encodePassword;
        final String exceptedResult = "User Register Successful";
        Mockito.when(objectMapper.convertValue(request, UserCreateRequest.class)).thenReturn(convertRequest);
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn(expectedEncodePassword);

        // Act
        String result = authService.authRegister(request);

        // Assert
        assertEquals(exceptedResult, result);
        Mockito.verify(objectMapper).convertValue(request, UserCreateRequest.class);
        Mockito.verify(passwordEncoder).encode(request.getPassword());
        Mockito.verify(userService).createUser(convertRequest);
    }


    @Test
    @DisplayName("[Method authLogin] Given user does not exist When logging in Then should throw not available exception")
    void givenUserDoesNotExist_whenLoggingIn_thenShouldThrowNotAvailableException() {
        // Arrange
        AuthLoginRequest request = TestData.authLoginRequest;
        String expectedResponse = String.format("User with the username %s not found.", request.getUsername());
        Mockito.when(userService.getUserByUsername(request.getUsername())).thenThrow(new NotAvailableException(expectedResponse));

        // Act
        NotAvailableException exception = assertThrows(NotAvailableException.class, () -> {
            authService.authLogin(request);
        });

        // Assert
        final String resultExceptionMessage = exception.getMessage();
        assertEquals(expectedResponse, resultExceptionMessage);
        Mockito.verify(userService).getUserByUsername(request.getUsername());
    }

    @Test
    @DisplayName("[Method authLogin] Given incorrect password When logging in Then should throw invalid exception")
    void givenIncorrectPassword_whenLoggingIn_thenShouldThrowInvalidException() {
        // Arrange
        AuthLoginRequest request = new AuthLoginRequest(TestData.username, "222");
        String expectedResponse = "Invalid Password";
        User expectedUser = TestData.fullUser;
        Mockito.when(userService.getUserByUsername(request.getUsername())).thenReturn(expectedUser);
        Mockito.when(passwordEncoder.matches(request.getPassword(), expectedUser.getPassword())).thenReturn(false);

        // Act
        InvalidException exception = Assertions.assertThrows(InvalidException.class, () -> {
            authService.authLogin(request);
        });

        // Assert
        final String resultExceptionMessage = exception.getMessage();
        assertEquals(expectedResponse, resultExceptionMessage);
        Mockito.verify(passwordEncoder).matches(request.getPassword(), expectedUser.getPassword());
    }


    @Test
    @DisplayName("[Method authLogin] Given valid credentials When logging in Then should return jwt token")
    void givenValidCredentials_whenLoggingIn_thenShouldReturnJwtToken() {
        // Arrange
        AuthLoginRequest request = TestData.authLoginRequest;
        User expectedUser = TestData.fullUser;
        final String expectedResponse = TestData.JWT_TOKEN;
        Mockito.when(userService.getUserByUsername(request.getUsername())).thenReturn(expectedUser);
        Mockito.when(passwordEncoder.matches(request.getPassword(), expectedUser.getPassword())).thenReturn(true);
        Mockito.when(jwtService.generateTokenWithClaims(expectedUser.getId(), expectedUser.getUserName(), expectedUser.getRole().getRoleName())).thenReturn(expectedResponse);

        // Act
        String result = authService.authLogin(request);

        // Assert
        assertEquals(expectedResponse, result);
        Mockito.verify(userService).getUserByUsername(request.getUsername());
        Mockito.verify(passwordEncoder).matches(request.getPassword(), expectedUser.getPassword());
        Mockito.verify(jwtService).generateTokenWithClaims(expectedUser.getId(), expectedUser.getUserName(), expectedUser.getRole().getRoleName());
    }
}