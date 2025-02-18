package com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.auth;

public class AuthLoginRequest {
    private String username;
    private String password;

    public AuthLoginRequest() {
    }

    public AuthLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
