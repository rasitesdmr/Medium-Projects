package com.rasitesdmr.springbootsecurityunitandintegrationtest.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.auth.service.AuthService;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.AlreadyAvailableException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.InvalidException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth.AuthRegisterRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt.JwtFilter;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt.JwtService;
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

    private static final String FIRST_NAME = "Raşit";
    private static final String LAST_NAME = "Eşdemir";
    private static final String USERNAME = "rasitesdmr";
    private static final String EMAIL = "rasitesdmr@gmail.com";
    private static final String PASSWORD = "123";


    @Test
    void authRegister_shouldReturn400_whenRequestIsInvalid() throws Exception {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD);
        String exceptedResponse = "{\"errors\":[\"The username is required.\"]}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verifyNoInteractions(authService);
    }

    @Test
    @DisplayName("Method authRegister should return 409 when username already exist")
    void authRegister_shouldReturn409_whenUsernameAlreadyExist() throws Exception {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        String exceptedResponse = "User with the username rasitesdmr already available.";

        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenThrow(new AlreadyAvailableException(exceptedResponse));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("Method authRegister should return 404 when role name not available")
    void authRegister_shouldReturn404_whenRoleNameNotAvailable() throws Exception {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        String exceptedResponse = "Role with the role name ROLE_USER not found.";

        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenThrow(new NotAvailableException(exceptedResponse));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("Method authRegister should return 500 when save user fails")
    void authRegister_shouldReturn500_whenSaveUserFails() throws Exception {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        String exceptedResponse = "User Not Saved : Exception message";

        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenThrow(new InternalServerErrorException(exceptedResponse));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("Method authRegister should return 201 when valid successfully")
    void authRegister_shouldReturn201_whenValidSuccessfully() throws Exception {
        AuthRegisterRequest request = new AuthRegisterRequest(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        String exceptedResponse = "User Register Successful";

        Mockito.when(authService.authRegister(Mockito.any(AuthRegisterRequest.class))).thenReturn(exceptedResponse);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/register")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verify(authService).authRegister(Mockito.any(AuthRegisterRequest.class));
        Mockito.verifyNoMoreInteractions(authService);

    }

    @Test
    @DisplayName("Method authLogin should return 201 when login successfully")
    void authLogin_shouldReturn201_whenLoginSuccessfully() throws Exception {
        AuthLoginRequest request = new AuthLoginRequest(USERNAME, PASSWORD);
        String exceptedResponse = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiNjU1N2ViMTUtZWU4Yi00ZGYwLWIyMmYtODg5NGEzMmQ1OTljIiwiaWF0IjoxNzM0OTU5ODY3LCJleHAiOjE3MzUwNDYyNjd9.Zf8FS4qlJP9-E05kpqAhaXZRoZySRgFKW-IiCdiuKD4";

        Mockito.when(authService.authLogin(Mockito.any(AuthLoginRequest.class))).thenReturn(exceptedResponse);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/login")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verify(authService).authLogin(Mockito.any(AuthLoginRequest.class));
        Mockito.verifyNoMoreInteractions(authService);

    }

    @Test
    void authLogin_shouldReturn404_whenUsernameNotFound() throws Exception {
        AuthLoginRequest request = new AuthLoginRequest(USERNAME, PASSWORD);
        String exceptedResponse = "User with the username rasitesdmr not found";

        Mockito.when(authService.authLogin(Mockito.any(AuthLoginRequest.class))).thenThrow(new NotAvailableException(exceptedResponse));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/login")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verify(authService).authLogin(Mockito.any(AuthLoginRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("Method authLogin should return 406 when password invalid")
    void authLogin_shouldReturn406_whenPasswordInvalid() throws Exception {
        AuthLoginRequest request = new AuthLoginRequest(USERNAME, PASSWORD);
        String exceptedResponse = "Invalid Password";

        Mockito.when(authService.authLogin(Mockito.any(AuthLoginRequest.class))).thenThrow(new InvalidException(exceptedResponse));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auths/login")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().string(exceptedResponse));

        Mockito.verify(authService).authLogin(Mockito.any(AuthLoginRequest.class));
        Mockito.verifyNoMoreInteractions(authService);
    }

}