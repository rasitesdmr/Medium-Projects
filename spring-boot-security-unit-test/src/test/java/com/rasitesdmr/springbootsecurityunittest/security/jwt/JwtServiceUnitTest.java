package com.rasitesdmr.springbootsecurityunittest.security.jwt;

import com.rasitesdmr.springbootsecurityunittest.data.TestData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceUnitTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", TestData.secretKey);
    }

    @Test
    @DisplayName("[Method generateTokenWithClaims] Given valid inputs When generating token Then should include username and role")
    void givenValidInputs_whenGeneratingToken_thenShouldIncludeUsernameAndRole() {
        // Arrange
        final UUID userId = TestData.userId;
        final String username = TestData.username;
        final String role = TestData.roleName;

        // Act
        String token = jwtService.generateTokenWithClaims(userId, username, role);

        // Assert
        assertNotNull(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(TestData.secretKey))).build().parseClaimsJws(token).getBody();
        assertEquals(username, claims.get("username"));
        assertEquals(role, claims.get("role"));
        assertEquals(userId.toString(), claims.getSubject());
    }

    @Test
    @DisplayName("[Method createJwtToken] Given valid claims and subject When creating jwt token Then should include username role and subject")
    void givenValidClaimsAndSubject_whenCreatingJwtToken_thenShouldIncludeUsernameRoleAndSubject() {
        // Arrange
        final String username = TestData.username;
        final String role = TestData.roleName;
        String subject = TestData.userId.toString();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role", role);

        // Act
        String token = jwtService.createJwtToken(subject, claims);

        // Assert
        assertNotNull(token);
        Claims extractedClaims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(TestData.secretKey))).build().parseClaimsJws(token).getBody();
        assertEquals(username, extractedClaims.get("username"));
        assertEquals(role, extractedClaims.get("role"));
        assertEquals(subject, extractedClaims.getSubject());
    }

    @Test
    @DisplayName("[Method extractUserIdFromToken] Given valid token When extracting user id Then should return correct user id")
    void givenValidToken_whenExtractingUserId_thenShouldReturnCorrectUserId() {
        // Arrange
        final String token = TestData.JWT_TOKEN;
        final UUID expectedUserId = UUID.fromString("b197630e-cb38-47c5-8a59-94a5fa86f266");
        // Act
        UUID resultUserId = jwtService.extractUserIdFromToken(token);
        // Assert
        assertEquals(expectedUserId, resultUserId);
    }

    @Test
    @DisplayName("[Method extractUserNameFromToken] Given valid token When extracting username Then should return correct username")
    void givenValidToken_whenExtractingUserName_thenShouldReturnCorrectUserName() {
        // Arrange
        final String expectedUsername = TestData.username;
        final String token = TestData.JWT_TOKEN;
        // Act
        String resultUsername = jwtService.extractUserNameFromToken(token);
        // Assert
        assertEquals(expectedUsername, resultUsername);
    }

    @Test
    @DisplayName("[Method getSiginKey] Given secret key is set When retrieving sign key Then should return non null hmac sha key")
    void givenSecretKeyIsSet_whenRetrievingSignKey_thenShouldReturnNonNullHmacSHAKey(){
        // Act
        Key key = jwtService.getSiginKey();
        // Assert
        assertNotNull(key);
        assertTrue(key.getAlgorithm().contains("HmacSHA"));
    }

    @Test
    @DisplayName("[Method extractAllClaims] Given valid jwt token When extracting claims Then should return all claims")
    void givenValidJwtToken_whenExtractingClaims_thenShouldReturnAllClaims() {
        // Arrange
        final String username = TestData.username;
        final String role = TestData.roleName;
        final String subject = "b197630e-cb38-47c5-8a59-94a5fa86f266";
        final String token = TestData.JWT_TOKEN;
        // Act
        Claims result = jwtService.extractAllClaims(token);
        // Assert
        assertEquals(username, result.get("username"));
        assertEquals(role, result.get("role"));
        assertEquals(subject, result.getSubject());
    }

    @Test
    @DisplayName("[Method isTokenValid] Given valid token When checking validity Then should return true")
    void givenValidToken_whenCheckingValidity_thenShouldReturnTrue() {
        // Arrange
        final String token = TestData.JWT_TOKEN;
        final String username = TestData.username;
        final String password = TestData.encodePassword;
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username,
                password,
                new ArrayList<>()
        );
        // Act
        boolean valid = jwtService.isTokenValid(token, userDetails);
        // assert
        assertTrue(valid);
    }

    @Test
    @DisplayName("[Method isTokenValid] Given mismatched username When checking validity Then should return false")
    void givenMismatchedUsername_whenCheckingValidity_thenShouldReturnFalse() {
        // Arrange
        final String token = TestData.JWT_TOKEN;
        final String password = TestData.encodePassword;
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "omeresdmr",
                password,
                new ArrayList<>()
        );
        // Act
        boolean valid = jwtService.isTokenValid(token, userDetails);
        // Assert
        assertFalse(valid);
    }


    @Test
    @DisplayName("[Method isTokenExpired] Given non expired token When checking expiration Then should return false")
    void givenNonExpiredToken_whenCheckingExpiration_thenShouldReturnFalse() {
       // Arrange
        final String token = TestData.JWT_TOKEN;
        // Act
        boolean expired = jwtService.isTokenExpired(token);
        // Assert
        assertFalse(expired);
    }
}