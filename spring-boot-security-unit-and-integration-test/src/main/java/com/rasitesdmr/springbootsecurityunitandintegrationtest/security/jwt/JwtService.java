package com.rasitesdmr.springbootsecurityunitandintegrationtest.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    @Value(value = "${secret.key}")
    private String secretKey;

    public String generateTokenWithClaims(UUID userId, String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role",role);
        String subject = String.valueOf(userId);
        return createJwtToken(subject, claims);
    }

    public String createJwtToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getTwentyFourHoursLaterDate())
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public UUID extractUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        String subject = claims.getSubject();
        return UUID.fromString(subject);
    }

    public String extractUserNameFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return String.valueOf(claims.get("username"));
    }

    public Key getSiginKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public Date getTwentyFourHoursLaterDate() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, 24);
        return c.getTime();
    }
}
