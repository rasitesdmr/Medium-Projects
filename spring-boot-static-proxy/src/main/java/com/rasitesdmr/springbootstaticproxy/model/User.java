package com.rasitesdmr.springbootstaticproxy.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private UUID id;
    private String username;
    private String email;
    private boolean isSave;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
