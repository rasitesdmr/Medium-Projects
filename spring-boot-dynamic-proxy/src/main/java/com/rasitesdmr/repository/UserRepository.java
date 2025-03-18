package com.rasitesdmr.repository;

import com.rasitesdmr.annotation.Repository;
import com.rasitesdmr.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class UserRepository implements JpaRepository {

    private final List<User> createUsers = new ArrayList<>();

    @Override
    public User save(User user) {
        user.setId(UUID.randomUUID());
        createUsers.add(user);
        return user;
    }

    @Override
    public User findById(UUID id) {
        return createUsers.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }
}
