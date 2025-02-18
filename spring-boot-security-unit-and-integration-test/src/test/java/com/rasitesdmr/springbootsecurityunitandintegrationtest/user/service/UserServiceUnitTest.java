package com.rasitesdmr.springbootsecurityunitandintegrationtest.user.service;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.enums.RoleEnum;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.AlreadyAvailableException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.service.RoleService;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.User;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.repository.UserRepository;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

   // @Mock
    private UserRepository userRepository;

    //@Mock
    private RoleService roleService;

    //@InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleService = Mockito.mock(RoleService.class);
        userService = new UserServiceImpl(userRepository, roleService);
    }

    @Test
    @DisplayName("Method saveUser should save successfully")
    void saveUser_shouldSaveSuccessfully() {
        User user = createUser();

        Mockito.when(userRepository.save(user)).thenReturn(user);
        assertDoesNotThrow(() -> userService.saveUser(user));

        Mockito.verify(userRepository).save(user);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Method saveUser should throw exception when save fails")
    void saveUser_shouldThrowException_whenSaveFails() {
        User user = createUser();

        Mockito.doThrow(new InternalServerErrorException("Database Error")).when(userRepository).save(Mockito.any(User.class));
        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertEquals("User Not Saved : Database Error", exception.getMessage());

        Mockito.verify(userRepository).save(user);
    }


    @Test
    @DisplayName("Method createUser should throw exception when username exists")
    void createUser_shouldThrowException_whenUsernameExists() {
        UserCreateRequest request = createUserRequest();

        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(true);

        AlreadyAvailableException exception = assertThrows(AlreadyAvailableException.class, () -> {
            userService.createUser(request);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals(String.format("User with the username %s already available.", request.getUserName()), exception.getMessage());

        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verifyNoMoreInteractions(userRepository, roleService);
    }

    @Test
    @DisplayName("Method createUser should throw exception when role does not exist")
    void createUser_shouldThrowException_whenRoleDoesNotExist() {
        UserCreateRequest request = createUserRequest();

        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(false);
        Mockito.when(roleService.getRoleByName(request.getRoleName())).thenThrow(new NotAvailableException(String.format("Role with the role name %s not found.", request.getRoleName())));

        NotAvailableException exception = assertThrows(NotAvailableException.class, () -> {
            userService.createUser(request);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Role with the role name ROLE_USER not found.", exception.getMessage());

        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verify(roleService).getRoleByName(request.getRoleName());
        Mockito.verifyNoMoreInteractions(userRepository, roleService);
    }


    @Test
    @DisplayName("Method createUser should throw exception when save user fails")
    void createUser_shouldThrowException_whenSaveUserFails() {
        UserCreateRequest request = createUserRequest();
        Role role = createRole();

        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(false);
        Mockito.when(roleService.getRoleByName(role.getRoleName())).thenReturn(role);
        Mockito.doThrow(new InternalServerErrorException("Database Error")).when(userRepository).save(Mockito.any(User.class));

        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class, () -> userService.createUser(request));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertEquals("User Not Saved : Database Error", exception.getMessage());

        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verify(roleService).getRoleByName(role.getRoleName());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Method createUser should create successfully")
    void createUser_shouldCreateSuccessfully() {
        UserCreateRequest request = createUserRequest();
        Role role = createRole();
        Mockito.when(userRepository.existsByUserName(request.getUserName())).thenReturn(false);
        Mockito.when(roleService.getRoleByName(role.getRoleName())).thenReturn(role);

        userService.createUser(request);

        Mockito.verify(userRepository).existsByUserName(request.getUserName());
        Mockito.verify(roleService).getRoleByName(role.getRoleName());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Method getUserByUsername should return user when username exists")
    void getUserByUsername_shouldReturnUser_whenUsernameExists() {
        String requestUsername = "rasitesdmr";
        User exceptedUser = createUser();

        Mockito.when(userRepository.findByUserName(requestUsername)).thenReturn(Optional.of(exceptedUser));

        User resultUser = userService.getUserByUsername(requestUsername);

        assertNotNull(resultUser);
        assertEquals(exceptedUser, resultUser);

        Mockito.verify(userRepository).findByUserName(requestUsername);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Method getUserByUsername should throw exception when username does not exist")
    void getUserByUsername_shouldThrowException_whenUsernameDoesNotExist() {
        String username = "esdmr";

        Mockito.when(userRepository.findByUserName(username)).thenReturn(Optional.empty());

        NotAvailableException exception = assertThrows(NotAvailableException.class, () -> {
            userService.getUserByUsername(username);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User with the username esdmr not found.", exception.getMessage());

        Mockito.verify(userRepository).findByUserName(username);
        Mockito.verifyNoMoreInteractions(userRepository);

    }

    @Test
    @DisplayName("Method getAllUsers should return all users when user exist")
    void getAllUsers_shouldReturnAllUsers_whenUserExist() {
        List<User> exceptedUser = createUsers();

        Mockito.when(userRepository.findAll()).thenReturn(exceptedUser);

        List<User> resultUsers = userService.getAllUsers();

        assertNotNull(resultUsers);
        assertEquals(exceptedUser.size(), resultUsers.size());
        assertEquals(exceptedUser, resultUsers);

        Mockito.verify(userRepository).findAll();
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Method getAllUsers should return empty list when no users exist")
    void getAllUsers_shouldReturnEmptyList_whenNoUsersExist() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> resultUser = userService.getAllUsers();

        assertNotNull(resultUser);
        assertTrue(resultUser.isEmpty());

        Mockito.verify(userRepository).findAll();
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Method getUserByToken should return user when user Id exists")
    void getUserByToken_shouldReturnUser_whenUserIdExists() {
        User excepteduser = createUser();
        UUID requestUserId = excepteduser.getId();

        try(MockedStatic<AuthUtil> mockedAuthUtilStatic = Mockito.mockStatic(AuthUtil.class)) {
            mockedAuthUtilStatic.when(AuthUtil::getUserIdByToken).thenReturn(requestUserId);
            Mockito.when(userRepository.findById(requestUserId)).thenReturn(Optional.of(excepteduser));
            User resultUser = userService.getUserByToken();

            assertNotNull(resultUser);
            assertEquals(excepteduser, resultUser);
            Mockito.verify(userRepository).findById(requestUserId);
            Mockito.verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    @DisplayName("Method getUserByToken should throw exception when user not found")
    void getUserByToken_shouldThrowException_whenUserNotFound(){
        UUID requestUserId = UUID.randomUUID();

        try(MockedStatic<AuthUtil> mockedAuthUtilStatic = Mockito.mockStatic(AuthUtil.class)) {
            mockedAuthUtilStatic.when(AuthUtil::getUserIdByToken).thenReturn(requestUserId);
            Mockito.when(userRepository.findById(requestUserId)).thenReturn(Optional.empty());

            NotAvailableException exception = assertThrows(NotAvailableException.class, () -> userService.getUserByToken());

            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals(String.format("User with the ID %s not found.",requestUserId), exception.getMessage());

            Mockito.verify(userRepository).findById(requestUserId);
            Mockito.verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    @DisplayName("Method getUserByToken should return null when auth util returns null")
    void getUserByToken_shouldReturnNull_whenAuthUtilReturnsNull(){
        try(MockedStatic<AuthUtil> mockedAuthUtilStatic = Mockito.mockStatic(AuthUtil.class)) {
            mockedAuthUtilStatic.when(AuthUtil::getUserIdByToken).thenReturn(null);

            User resultUser = userService.getUserByToken();

            assertNull(resultUser);
            Mockito.verifyNoInteractions(userRepository);
        }
    }


    private UserCreateRequest createUserRequest() {
        String requestFirstName = "Raşit";
        String requestLastName = "Eşdemir";
        String requestUserName = "rasitesdmr";
        String requestEmail = "rasitesdmr@gmail.com";
        String requestPassword = "rasit123";
        String requestRoleName = "ROLE_USER";
        return new UserCreateRequest(requestFirstName, requestLastName, requestUserName, requestEmail, requestPassword, requestRoleName);
    }

    private User createUser() {
        UUID id = UUID.randomUUID();
        String firstName = "Raşit";
        String lastName = "Eşdemir";
        String userName = "rasitesdmr";
        String email = "rasitesdemir7@gmail.com";
        String password = "dksafds";
        String oldPassword = "dksafds";
        Date createdDate = new Date();
        Date updatedDate = new Date();
        return new User(id, firstName, lastName, userName, email, password, oldPassword, createdDate, updatedDate, createRole());
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        users.add(createUser());
        users.add(createUser());
        users.add(createUser());
        users.add(createUser());
        return users;
    }

    private Role createRole() {
        UUID id = UUID.randomUUID();
        String roleName = RoleEnum.ROLE_USER.name();
        Date createdDate = new Date();
        Date updatedDate = new Date();
        return new Role(id, roleName, createdDate, updatedDate);
    }
}