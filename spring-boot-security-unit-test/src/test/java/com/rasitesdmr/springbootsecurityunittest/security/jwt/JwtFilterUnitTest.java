package com.rasitesdmr.springbootsecurityunittest.security.jwt;

import com.rasitesdmr.springbootsecurityunittest.security.userdetails.CustomUserDetailsService;
import com.rasitesdmr.springbootsecurityunittest.data.TestData;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;

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


    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("[Method doFilterInternal] Given missing authorization header When jwt filter runs Then should proceed with filter chain")
    void givenMissingAuthorizationHeader_whenJwtFilterRuns_thenShouldProceedWithFilterChain() throws ServletException, IOException {
        // Arrange
        Mockito.when(request.getHeader(TestData.HEADER)).thenReturn(null);

        // Act
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(jwtService, customUserDetailsService);
    }

    @Test
    @DisplayName("[Method doFilterInternal] Given invalid authorization header When jwt filter runs Then should proceed with filter chain")
    void givenInvalidAuthorizationHeader_whenJwtFilterRuns_thenShouldProceedWithFilterChain() throws ServletException, IOException {
        // Arrange
        Mockito.when(request.getHeader(TestData.HEADER)).thenReturn("Invalid Header");

        // Act
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(filterChain).doFilter(request, response);
        Mockito.verifyNoInteractions(jwtService, customUserDetailsService);
    }

    @Test
    @DisplayName("[Method doFilterInternal] Given valid token When jwt filter runs Then should set security context")
    void givenValidToken_whenJwtFilterRuns_thenShouldSetSecurityContext() throws ServletException, IOException {
        // Arrange
        Mockito.when(request.getHeader(TestData.HEADER)).thenReturn(TestData.BEARER_TOKEN);
        Mockito.when(jwtService.extractUserNameFromToken(TestData.JWT_TOKEN)).thenReturn(TestData.USERNAME);
        Mockito.when(customUserDetailsService.loadUserByUsername(TestData.USERNAME)).thenReturn(TestData.userDetails);
        Mockito.when(jwtService.isTokenValid(TestData.JWT_TOKEN, TestData.userDetails)).thenReturn(true);

        // Act
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Assert
        SecurityContext context = SecurityContextHolder.getContext();
        assertNotNull(context.getAuthentication());
        assertEquals(TestData.USERNAME, context.getAuthentication().getName());
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("[Method doFilterInternal] Given expired token When jwt filter runs Then should throw expired jwt exception")
    void givenExpiredToken_whenJwtFilterRuns_thenShouldThrowExpiredJwtException() throws ServletException, IOException {
        // Arrange
        Mockito.when(response.getWriter()).thenReturn(mockWriter);
        Mockito.when(request.getHeader(TestData.HEADER)).thenReturn(TestData.BEARER_TOKEN);
        Mockito.when(jwtService.extractUserNameFromToken(TestData.JWT_TOKEN)).thenThrow(new ExpiredJwtException(null, null, "Token Expired"));

        // Act
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
        Mockito.verify(mockWriter).write("Token Expired");
        Mockito.verifyNoInteractions(customUserDetailsService);
    }

    @Test
    @DisplayName("[Method doFilterInternal] Given invalid token When jwt filter runs Then should proceed with filter chain")
    void givenInvalidToken_whenJwtFilterRuns_thenShouldProceedWithFilterChain()  throws Exception {
        // Arrange
        Mockito.when(request.getHeader("Authorization")).thenReturn(TestData.BEARER_TOKEN);
        Mockito.when(jwtService.extractUserNameFromToken(TestData.JWT_TOKEN)).thenReturn(TestData.USERNAME);
        Mockito.when(customUserDetailsService.loadUserByUsername(TestData.USERNAME)).thenReturn(TestData.userDetails);
        Mockito.when(jwtService.isTokenValid(TestData.JWT_TOKEN, TestData.userDetails)).thenReturn(false);

        // Act
        jwtFilter.doFilterInternal(request, response, filterChain);

        // Assert
        Mockito.verify(filterChain).doFilter(request, response);
        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}