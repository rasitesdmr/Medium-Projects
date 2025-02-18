package com.rasitesdmr.springbootsecurityunitandintegrationtest.user.controller;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt.JwtFilter;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt.JwtService;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.User;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtFilter jwtFilter;


    @Test
    @DisplayName("Method getAllUsers should return 200 when user are present or absent")
    void getAllUsers_shouldReturn200_whenUsersArePresentOrAbsent() throws Exception {
        List<User> exceptedUsers = exceptedUsers();

        Mockito.when(userService.getAllUsers()).thenReturn(exceptedUsers);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/all")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(exceptedUsers.size()))
                .andExpect(jsonPath("$[0].firstName").value("Raşit"))
                .andExpect(jsonPath("$[1].firstName").value("Ömer"));

        Mockito.verify(userService).getAllUsers();
    }


    @Test
    @DisplayName("Method getUserByToken should return 200")
    void getUserByToken_shouldReturn200() throws Exception {
        User exceptedUser = exceptedUser();

        Mockito.when(userService.getUserByToken()).thenReturn(exceptedUser);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Raşit"))
                .andExpect(jsonPath("$.lastName").value("Eşdemir"))
                .andExpect(jsonPath("$.userName").value("rasitesdmr"))
                .andExpect(jsonPath("$.email").value("rasitesdmr@gmail.com"));

        Mockito.verify(userService).getUserByToken();
    }

    @Test
    @DisplayName("Method getUserByToken should return 404 when user not found in database")
    void getUserByToken_shouldReturn404_whenUserNotFoundInDatabase() throws Exception {
        UUID authUserId = UUID.fromString("e362e91c-a363-42c7-a46e-ecd94a40521");

        Mockito.when(userService.getUserByToken()).thenThrow(new NotAvailableException(String.format("User with the ID %s not found.", authUserId)));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.format("User with the ID %s not found.", authUserId)));

        Mockito.verify(userService).getUserByToken();
    }

    private User exceptedUser(){
        UUID user1 = UUID.randomUUID();
        return new User(user1, "Raşit", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "password", "password", new Date(), new Date(), null);
    }

    private List<User> exceptedUsers() {
        List<User> users = new ArrayList<>();
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID user3 = UUID.randomUUID();
        users.add(new User(user1, "Raşit", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "password", "password", new Date(), new Date(), null));
        users.add(new User(user2, "Ömer", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "password", "password", new Date(), new Date(), null));
        users.add(new User(user3, "Turgay", "Eşdemir", "rasitesdmr", "rasitesdmr@gmail.com", "password", "password", new Date(), new Date(), null));
        return users;
    }
}