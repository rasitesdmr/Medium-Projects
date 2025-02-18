package com.rasitesdmr.springbootsecurityunittest.user.service;

import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.AlreadyAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunittest.role.Role;
import com.rasitesdmr.springbootsecurityunittest.role.service.RoleService;
import com.rasitesdmr.springbootsecurityunittest.user.User;
import com.rasitesdmr.springbootsecurityunittest.user.repository.UserRepository;
import com.rasitesdmr.springbootsecurityunittest.util.AuthUtil;
import com.rasitesdmr.springbootsecurityunittest.data.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    @DisplayName("[Method saveUser] Given database error occurs When saving use Then should throw internal server error exception")
    void givenDatabaseErrorOccurs_whenSavingUser_thenShouldThrowInternalServerErrorException() {
       // Arrange
        User user = TestData.fullUser;
        final String expectedErrorMessage = "User Not Saved : Database Error";
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(new InternalServerErrorException("Database Error"));

        // Act
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> {
            userService.saveUser(user);
        });

        // Assert
        final String resultErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultErrorMessage);
    }

    @Test
    @DisplayName("[Method saveUser] Given valid user data When saving user Then should save successfully")
    void givenValidUserData_whenSavingUser_thenShouldSaveSuccessfully() {
       // Arrange
        User user = TestData.fullUser;
        Mockito.when(userRepository.save(user)).thenReturn(user);

        // Act
        assertDoesNotThrow(() -> userService.saveUser(user));

        // Assert
        Mockito.verify(userRepository).save(user);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("[Method createUser] Given user exist When creating user Then should throw already available exception")
    void givenUserExists_whenCreatingUser_thenShouldThrowAlreadyAvailableException() {
        // Arrange
        UserCreateRequest request = TestData.userCreateRequest;
        final String expectedErrorMessage = String.format("User with the username %s already available.", request.getUserName());
        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(true);

        // Act
        AlreadyAvailableException exception = assertThrows(AlreadyAvailableException.class, () -> {
            userService.createUser(request);
        });

        // Assert
        final String resultErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultErrorMessage);
        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    @DisplayName("[Method createUser] Given role does not exist When creating user Then should throw not available exception")
    void givenRoleDoesNotExist_whenCreatingUser_thenShouldThrowNotAvailableException() {
        // Arrange
        UserCreateRequest request = TestData.userCreateRequest;
        final String mockitoErrorMessage = String.format("Role with the role name %s not found.", request.getRoleName());
        final String expectedErrorMessage = String.format("Role with the role name %s not found.", request.getRoleName());
        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(false);
        Mockito.when(roleService.getRoleByName(request.getRoleName())).thenThrow(new NotAvailableException(mockitoErrorMessage));

        // Act
        NotAvailableException exception = assertThrows(NotAvailableException.class, () -> {
            userService.createUser(request);
        });

        // Assert
        final String resultErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultErrorMessage);
        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verify(roleService).getRoleByName(request.getRoleName());
        Mockito.verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    @DisplayName("[Method createUser] Given saving user fails When creating user Then should throw internal server error exception")
    void givenSavingUserFails_whenCreatingUser_thenShouldThrowInternalServerErrorException() {
        // Arrange
        UserCreateRequest request = TestData.userCreateRequest;
        Role role = TestData.userRole;
        final String mockitoErrorMessage = "Database Error";
        final String expectedErrorMessage = "User Not Saved : Database Error";
        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(false);
        Mockito.when(roleService.getRoleByName(role.getRoleName())).thenReturn(role);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(new InternalServerErrorException(mockitoErrorMessage));

        // Act
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> {
            userService.createUser(request);
        });

        // Assert
        final String resultErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultErrorMessage);
        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verify(roleService).getRoleByName(role.getRoleName());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("[Method createUser] Given user does not exist and role exists When creating user Then should create user successfully")
    void givenUserDoesNotExistAndRoleExists_whenCreatingUser_thenShouldCreateUserSuccessfully() {
        // Arrange
        UserCreateRequest request = TestData.userCreateRequest;
        Role role = TestData.userRole;
        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(false);
        Mockito.when(roleService.getRoleByName(role.getRoleName())).thenReturn(role);

        // Act
        userService.createUser(request);

        // Assert
        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verify(roleService).getRoleByName(role.getRoleName());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("[Method getUserByUsername] Given username does exist When retrieving user by username Then should throw not available exception")
    void givenUsernameDoesExist_whenRetrievingUserByUsername_thenShouldThrowNotAvailableException() {
        // Arrange
        final String requestUsername = TestData.username;
        final String expectedErrorMessage = String.format("User with the username %s not found.", requestUsername);
        Mockito.when(userRepository.findByUserName(requestUsername)).thenReturn(Optional.empty());

        // Act
        NotAvailableException exception = assertThrows(NotAvailableException.class, () -> {
            userService.getUserByUsername(requestUsername);
        });

        // Assert
        final String resultErrorMessage = exception.getMessage();
        assertEquals(expectedErrorMessage, resultErrorMessage);
        Mockito.verify(userRepository).findByUserName(requestUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("[Method getUserByUsername] Given username exist When retrieving user by username Then should return user")
    void givenUsernameExist_whenRetrievingUserByUsername_thenShouldReturnUser() {
        // Arrange
        final String requestUsername = TestData.username;
        final User expectedUser = TestData.fullUser;
        Mockito.when(userRepository.findByUserName(requestUsername)).thenReturn(Optional.of(expectedUser));

        // Act
        User resultUser = userService.getUserByUsername(requestUsername);

        // Assert
        assertNotNull(resultUser);
        assertEquals(expectedUser, resultUser);
        Mockito.verify(userRepository).findByUserName(requestUsername);
        Mockito.verifyNoMoreInteractions(userRepository);

    }

    @Test
    @DisplayName("[Method checkExistUserName] Given username does exist When checking existence Then should return false")
    void givenUsernameDoesExist_whenCheckingExistence_thenShouldReturnFalse() {
        // Arrange
        final String requestUsername = TestData.username;
        final boolean expectedResult = false;
        Mockito.when(userRepository.existsByUserName(requestUsername)).thenReturn(expectedResult);

        // Act
        boolean result = userService.checkExistUserName(requestUsername);

        // Assert
        assertEquals(expectedResult, result);
        Mockito.verify(userRepository).existsByUserName(requestUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("[Method checkExistUserName] Given username exist When checking existence Then should return true")
    void givenUsernameExist_whenCheckingExistence_thenShouldReturnTrue() {
        // Arrange
        final String requestUsername = TestData.username;
        final boolean expectedResult = true;
        Mockito.when(userRepository.existsByUserName(requestUsername)).thenReturn(expectedResult);

        // Act
        boolean result = userService.checkExistUserName(requestUsername);

        // Assert
        assertEquals(expectedResult, result);
        Mockito.verify(userRepository).existsByUserName(requestUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("[Method getAllUsers] Given user exist When fetching all users Then should return user list")
    void givenUserExist_whenFetchingAllUsers_thenShouldReturnUserList() {
        // Arrange
        List<User> expectedUsers = TestData.userList;
        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> resultUsers = userService.getAllUsers();

        // Assert
        assertNotNull(resultUsers);
        assertEquals(expectedUsers.size(), resultUsers.size());
        assertEquals(expectedUsers, resultUsers);
        Mockito.verify(userRepository).findAll();
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("[Method getUserByToken] Given user id is null When fetching user by token Then should return null")
    void givenUserIdIsNull_whenFetchingUserByToken_thenShouldReturnNull() {
        try (MockedStatic<AuthUtil> mockedAuthUtilStatic = Mockito.mockStatic(AuthUtil.class)) {
            // Arrange
            mockedAuthUtilStatic.when(AuthUtil::getUserIdByToken).thenReturn(null);
            // Act
            User result = userService.getUserByToken();
            // Assert
            assertNull(result);
        }
    }

    @Test
    @DisplayName("[Method getUserByToken] Given user does not exist When fetching user by token Then should throw not available exception")
    void givenUserDoesNotExist_whenFetchingUserByToken_thenShouldThrowNotAvailableException(){
        // Arrange
        final UUID expectedUserId = TestData.userId;
        final String expectedErrorMessage = String.format("User with the ID %s not found.", expectedUserId);
        try (MockedStatic<AuthUtil> mockedAuthUtilStatic = Mockito.mockStatic(AuthUtil.class)) {
            // Arrange
            mockedAuthUtilStatic.when(AuthUtil::getUserIdByToken).thenReturn(expectedUserId);
            Mockito.when(userRepository.findById(expectedUserId)).thenThrow(new NotAvailableException(expectedErrorMessage));

            // Act
            NotAvailableException exception = assertThrows(NotAvailableException.class, () -> {
                userService.getUserByToken();
            });

            // Assert
            final String resultErrorMessage = exception.getMessage();
            assertEquals(expectedErrorMessage, resultErrorMessage);
            Mockito.verify(userRepository).findById(expectedUserId);
            Mockito.verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    @DisplayName("[Method getUserByToken] Given user exist When fetching user by token Then should return user")
    void givenUserExist_whenFetchingUserByToken_thenShouldReturnUser(){
        // Arrange
        final UUID expectedUserId = TestData.userId;
        final User expectedUser = TestData.fullUser;

        try (MockedStatic<AuthUtil> mockedAuthUtilStatic = Mockito.mockStatic(AuthUtil.class)) {
            // Arrange
            mockedAuthUtilStatic.when(AuthUtil::getUserIdByToken).thenReturn(expectedUserId);
            Mockito.when(userRepository.findById(expectedUserId)).thenReturn(Optional.of(expectedUser));

            // Act
            User result =  userService.getUserByToken();

            // Assert
            assertNotNull(result);
            assertEquals(expectedUser, result);
            Mockito.verify(userRepository).findById(expectedUserId);
            Mockito.verifyNoMoreInteractions(userRepository);
        }
    }
}