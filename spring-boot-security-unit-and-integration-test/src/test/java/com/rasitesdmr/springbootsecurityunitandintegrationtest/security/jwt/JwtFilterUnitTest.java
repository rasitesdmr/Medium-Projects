package com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.userdetails.CustomUserDetails;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.userdetails.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterUnitTest {

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private PrintWriter mockWriter;

    private final UserDetails userDetails = new CustomUserDetails(UUID.fromString("6557eb15-ee8b-4df0-b22f-8894a32d599c"), "rasitesdmr", "$2a$10$zInbSaCaBoavjOwZgUNH..PacmbgPQUq0YzF96IGAh9Z.ayJOABga", "ROLE_USER");
    private static final String HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiNjU1N2ViMTUtZWU4Yi00ZGYwLWIyMmYtODg5NGEzMmQ1OTljIiwiaWF0IjoxNzM0OTU5ODY3LCJleHAiOjE3MzUwNDYyNjd9.Zf8FS4qlJP9-E05kpqAhaXZRoZySRgFKW-IiCdiuKD4";
    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwidXNlcm5hbWUiOiJyYXNpdGVzZG1yIiwic3ViIjoiNjU1N2ViMTUtZWU4Yi00ZGYwLWIyMmYtODg5NGEzMmQ1OTljIiwiaWF0IjoxNzM0OTU5ODY3LCJleHAiOjE3MzUwNDYyNjd9.Zf8FS4qlJP9-E05kpqAhaXZRoZySRgFKW-IiCdiuKD4";
    private static final String USERNAME = "rasitesdmr";


    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Method doFilterInternal should proceed with filter chain when authorization header is missing")
    void doFilterInternal_shouldProceedWithFilterChain_whenAuthorizationHeaderIsMissing() throws ServletException, IOException {
        Mockito.when(request.getHeader(HEADER)).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(jwtService, customUserDetailsService);
    }

    @Test
    @DisplayName("Method doFilterInternal should proceed with filter chain when authorization header is invalid")
    void doFilterInternal_shouldProceedWithFilterChain_whenAuthorizationHeaderIsInvalid() throws ServletException, IOException {
        Mockito.when(request.getHeader(HEADER)).thenReturn("Invalid Header");

        jwtFilter.doFilterInternal(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(jwtService, customUserDetailsService);
    }

    @Test
    @DisplayName("Method doFilterInternal should set security context when token is valid")
    void doFilterInternal_shouldSetSecurityContext_whenTokenIsValid() throws ServletException, IOException {
        Mockito.when(request.getHeader(HEADER)).thenReturn(BEARER_TOKEN);
        Mockito.when(jwtService.extractUserNameFromToken(JWT_TOKEN)).thenReturn(USERNAME);
        Mockito.when(customUserDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        Mockito.when(jwtService.isTokenValid(JWT_TOKEN, userDetails)).thenReturn(true);

        jwtFilter.doFilterInternal(request, response, filterChain);

        SecurityContext context = SecurityContextHolder.getContext();
        assertNotNull(context.getAuthentication());
        assertEquals(USERNAME, context.getAuthentication().getName());
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldReturn401_whenTokenIsExpired() throws ServletException, IOException {
        Mockito.when(response.getWriter()).thenReturn(mockWriter);
        Mockito.when(request.getHeader(HEADER)).thenReturn(BEARER_TOKEN);
        Mockito.when(jwtService.extractUserNameFromToken(JWT_TOKEN)).thenThrow(new ExpiredJwtException(null, null, "Token Expired"));

        jwtFilter.doFilterInternal(request, response, filterChain);

        Mockito.verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
        Mockito.verify(mockWriter).write("Token Expired");
        Mockito.verifyNoInteractions(customUserDetailsService);
    }

    @Test
    @DisplayName("Should proceed with filter chain when token is invalid")
    void doFilterInternal_shouldProceedWithFilterChain_whenTokenIsInvalid() throws Exception {
        Mockito.when(request.getHeader("Authorization")).thenReturn(BEARER_TOKEN);
        Mockito.when(jwtService.extractUserNameFromToken(JWT_TOKEN)).thenReturn(USERNAME);
        Mockito.when(customUserDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        Mockito.when(jwtService.isTokenValid(JWT_TOKEN, userDetails)).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
