package com.rasitesdmr.springbootsecurityunittest.data;

import com.rasitesdmr.springbootsecurityunittest.domain.enums.RoleEnum;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthLoginRequest;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth.AuthRegisterRequest;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunittest.role.Role;
import com.rasitesdmr.springbootsecurityunittest.security.userdetails.CustomUserDetails;
import com.rasitesdmr.springbootsecurityunittest.user.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestData {

    // User Variable
    public static final UUID userId = UUID.randomUUID();
    public static final String firstName = "Raşit";
    public static final String lastName = "Eşdemir";
    public static final String username = "rasitesdmr";
    public static final String email = "rasitesdemir7@gmail.com";
    public static final String password = "123";
    public static final String encodePassword = "$2a$10$/wXgZ.C2WIMG9lvy5Kiv/.bblSmmBeQrP1lNHv/DpzYbNmnz6J2k.";
    public static final String encodeOldPassword = "$2a$10$/wXgZ.C2WIMG9lvy5Kiv/.bblSmmBeQrP1lNHv/DpzYbNmnz6J2k.";
    public static final Date userCreatedDate = new Date();
    public static final Date userUpdatedDate = new Date();

    // Role Variable
    public static final UUID roleId = UUID.randomUUID();
    public static final String roleName = RoleEnum.ROLE_USER.name();
    public static final Date roleCreatedDate = new Date();
    public static final Date roleUpdatedDate = new Date();

    // JWT
    public static final String HEADER = "Authorization";
    public static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiNjU1N2ViMTUtZWU4Yi00ZGYwLWIyMmYtODg5NGEzMmQ1OTljIiwiaWF0IjoxNzM0OTU5ODY3LCJleHAiOjE3MzUwNDYyNjd9.Zf8FS4qlJP9-E05kpqAhaXZRoZySRgFKW-IiCdiuKD4";
    public static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiYjE5NzYzMGUtY2IzOC00N2M1LThhNTktOTRhNWZhODZmMjY2IiwiaWF0IjoxNzM4MDA3MzcyLCJleHAiOjE3MzgwOTM3NzJ9.6gl5lsNmQH-O98AXqhr3_ARI9PS_Yn1gNkqCTIBHwQQ";
    public static final String expiredJwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiNjU1N2ViMTUtZWU4Yi00ZGYwLWIyMmYtODg5NGEzMmQ1OTljIiwiaWF0IjoxNzM0OTU5ODY3LCJleHAiOjE3MzUwNDYyNjd9.Zf8FS4qlJP9-E05kpqAhaXZRoZySRgFKW-IiCdiuKD4";
    public static final String USERNAME = "rasitesdmr";

    // Model
    public static Role userRole = new Role(roleId, roleName, roleCreatedDate, roleUpdatedDate);
    public static User fullUser = new User(userId, firstName, lastName, username, email, encodePassword, encodeOldPassword, userCreatedDate, userUpdatedDate, userRole);
    public static UserCreateRequest userCreateRequest = new UserCreateRequest(firstName, lastName, username, email, password, roleName);
    public static List<User> userList = List.of(fullUser, fullUser, fullUser, fullUser, fullUser);
    public final static UserDetails userDetails = new CustomUserDetails(UUID.fromString("6557eb15-ee8b-4df0-b22f-8894a32d599c"), "rasitesdmr", "$2a$10$zInbSaCaBoavjOwZgUNH..PacmbgPQUq0YzF96IGAh9Z.ayJOABga", "ROLE_USER");
    public final static AuthRegisterRequest authRegisterRequest = new AuthRegisterRequest(firstName, lastName, USERNAME, email, password);
    public final static AuthLoginRequest authLoginRequest = new AuthLoginRequest(username, password);

    public final static String secretKey = "413DSEKRYFJSUFN235u23423153958SDFUY482485200CN";
}
