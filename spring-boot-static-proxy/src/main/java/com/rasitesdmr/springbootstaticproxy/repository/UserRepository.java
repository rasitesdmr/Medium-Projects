package com.rasitesdmr.springbootstaticproxy.repository;

import com.rasitesdmr.springbootstaticproxy.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository  {

    private final List<User> createUsers = new ArrayList<>();

    public User save(User user) {
        user.setId(UUID.randomUUID());
        user.setSave(true);
        createUsers.add(user);
        return user;
    }

    public User findById(UUID id) {
        return createUsers.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }
}
