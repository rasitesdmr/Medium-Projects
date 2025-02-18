package com.rasitesdmr.springbootsecurityunittest.domain.model.request.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AuthRegisterRequest {
    @NotBlank(message = "The firstname is required.")
    private String firstName;
    @NotBlank(message = "The lastname is required.")
    private String lastName;
    @NotBlank(message = "The username is required.")
    private String userName;
    @NotBlank(message = "The email is required.")
    private String email;
    @NotBlank(message = "The password is required.")
    private String password;

    public AuthRegisterRequest() {
    }

    public AuthRegisterRequest(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static AuthRegisterRequestBuilder builder(){
        return new AuthRegisterRequestBuilder();
    }

    public static class AuthRegisterRequestBuilder{
        private String firstName;
        private String lastName;
        private String userName;
        private String email;
        private String password;

        public AuthRegisterRequestBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthRegisterRequestBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthRegisterRequestBuilder userName(final String userName) {
            this.userName = userName;
            return this;
        }

        public AuthRegisterRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public AuthRegisterRequestBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public AuthRegisterRequest build(){
            return new AuthRegisterRequest(firstName,lastName,userName,email,password);
        }
    }
}
