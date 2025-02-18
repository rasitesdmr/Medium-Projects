package com.rasitesdmr.springbootsecurityunittest.user.controller;

import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunittest.security.jwt.JwtFilter;
import com.rasitesdmr.springbootsecurityunittest.security.jwt.JwtService;
import com.rasitesdmr.springbootsecurityunittest.user.User;
import com.rasitesdmr.springbootsecurityunittest.user.service.UserService;
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

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
    @DisplayName("[Method getAllUsers] Given user exist When retrieving all users Then should return 200 and user list")
    void givenUserExist_whenRetrievingAllUsers_thenShouldReturn200AndUserList() throws Exception {
        // Arrange
        List<User> expectedUsers = TestData.userList;
        final String firstUsername = "rasitesdmr";
        final String secondUsername = "rasitesdmr";
        Mockito.when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/all")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(expectedUsers.size()))
                .andExpect(jsonPath("$[0].userName").value(firstUsername))
                .andExpect(jsonPath("$[1].userName").value(secondUsername));
        Mockito.verify(userService).getAllUsers();
    }


    @Test
    @DisplayName("[Method getUserByToken] Given user does not exist When get user by token called Then should return 404 not found")
    void givenUserDoesNotExist_whenGetUserByTokenCalled_thenShouldReturn404NotFound() throws Exception {
        // Arrange
        final UUID authUserId = TestData.userId;
        final String expectedErrorMessage = String.format("User with the ID %s not found.", authUserId);
        Mockito.when(userService.getUserByToken()).thenThrow(new NotAvailableException(expectedErrorMessage));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedErrorMessage));
        Mockito.verify(userService).getUserByToken();
    }

    @Test
    @DisplayName("[Method getUserByToken] Given user exist When retrieving user by token Then should return 200")
    void givenUserExist_whenRetrievingUserByToken_thenShouldReturn200() throws Exception {
        // Arrange
        User expectedUser = TestData.fullUser;
        final String firstname = "Raşit";
        final String lastname = "Eşdemir";
        Mockito.when(userService.getUserByToken()).thenReturn(expectedUser);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(firstname))
                .andExpect(jsonPath("$.lastName").value(lastname));
        Mockito.verify(userService).getUserByToken();
    }
}