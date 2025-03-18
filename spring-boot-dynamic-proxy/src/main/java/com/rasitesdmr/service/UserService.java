package com.rasitesdmr.service;

import com.rasitesdmr.domain.request.UserCreateRequest;
import com.rasitesdmr.model.User;

import java.util.UUID;

public interface UserService {
    User saveUser(User user);
    User createUser(UserCreateRequest userCreateRequest);
    User getUserById(UUID id);
}
