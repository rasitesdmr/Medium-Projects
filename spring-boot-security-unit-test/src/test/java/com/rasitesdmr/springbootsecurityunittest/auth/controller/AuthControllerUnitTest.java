package com.rasitesdmr.springbootsecurityunittest.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.springbootsecurityunittest.auth.service.AuthService;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.AlreadyAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.InvalidException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthRegisterRequest;
import com.rasitesdmr.springbootsecurityunittest.security.jwt.JwtFilter;
import com.rasitesdmr.springbootsecurityunittest.security.jwt.JwtService;
import com.rasitesdmr.springbootsecurityunittest.data.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtFilter jwtFilter;


    @Test
    @DisplayName("[Method authRegister] Given invalid request When registering Then should return 400 bad request")
    void givenInvalidRequest_whenRegistering_thenShouldReturn400BadRequest() throws Exception {
        // Arrange
        AuthRegisterRequest request = new AuthRegisterRequest(TestData.firstName, TestData.lastName, "", TestData.email, TestData.password);
        String expectedResponse = "{\"errors\":[\"The username is required.\"]}";

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verifyNoInteractions(authService);
    }

    @Test
    @DisplayName("[Method authRegister] Given existing username When registering Then should return 409 conflict")
    void givenExistingUsername_whenRegistering_thenShouldReturn409Conflict() throws Exception {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        String expectedResponse = "User with the username rasitesdmr already available.";
        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenThrow(new AlreadyAvailableException(expectedResponse));

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("[Method authRegister] Given unavailable role name When registering Then should return 404 not found")
    void givenUnavailableRoleName_whenRegistering_thenShouldReturn404NotFound() throws Exception {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        String expectedResponse = "Role with the role name ROLE_USER not found.";
        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenThrow(new NotAvailableException(expectedResponse));

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("[Method authRegister] Given user save failure When registering Then should return 500 internal server error")
    void givenUserSaveFailure_whenRegistering_thenShouldReturn500InternalServerError() throws Exception {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        String expectedResponse = "User Not Saved : Exception message";
        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenThrow(new InternalServerErrorException(expectedResponse));

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("[Method authRegister] Given valid user registration When registering Then should return 201 created")
    void givenValidUserRegistration_whenRegistering_thenShouldReturn201Created() throws Exception {
        // Arrange
        AuthRegisterRequest request = TestData.authRegisterRequest;
        String expectedResponse = "User Register Successful";
        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenReturn(expectedResponse);

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);

    }

    @Test
    @DisplayName("[Method authLogin] Given non existing username When logging in Then should return 404 not found")
    void givenNonExistingUsername_whenLoggingIn_thenShouldReturn404NotFound() throws Exception {
        // Arrange
        AuthLoginRequest request = TestData.authLoginRequest;
        String expectedResponse = "User with the username rasitesdmr not found";
        Mockito.when(authService.authLogin(Mockito.any(AuthLoginRequest.class))).thenThrow(new NotAvailableException(expectedResponse));

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/login")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verify(authService).authLogin(Mockito.any(AuthLoginRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("[Method authLogin] Given invalid password When logging in Then should return 406 not acceptable")
    void givenInvalidPassword_whenLoggingIn_thenShouldReturn406NotAcceptable() throws Exception {
        // Arrange
        AuthLoginRequest request = TestData.authLoginRequest;
        String expectedResponse = "Invalid Password";
        Mockito.when(authService.authLogin(Mockito.any(AuthLoginRequest.class))).thenThrow(new InvalidException(expectedResponse));

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/login")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verify(authService).authLogin(Mockito.any(AuthLoginRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("[Method authLogin] Given valid credentials When logging in Then should return 201 created")
    void givenValidCredentials_whenLoggingIn_thenShouldReturn201Created() throws Exception {
        // Arrange
        AuthLoginRequest request = TestData.authLoginRequest;
        String expectedResponse = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiNjU1N2ViMTUtZWU4Yi00ZGYwLWIyMmYtODg5NGEzMmQ1OTljIiwiaWF0IjoxNzM0OTU5ODY3LCJleHAiOjE3MzUwNDYyNjd9.Zf8FS4qlJP9-E05kpqAhaXZRoZySRgFKW-IiCdiuKD4";
        Mockito.when(authService.authLogin(Mockito.any(AuthLoginRequest.class))).thenReturn(expectedResponse);

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/login")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
        Mockito.verify(authService).authLogin(Mockito.any(AuthLoginRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }
}