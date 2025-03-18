package com.rasitesdmr.springbootstaticproxy.service;

import com.rasitesdmr.springbootstaticproxy.domain.request.UserCreateRequest;
import com.rasitesdmr.springbootstaticproxy.model.User;

import java.util.UUID;

public interface UserService {
    User saveUser(User user);
    User createUser(UserCreateRequest userCreateRequest);
    User getUserById(UUID id);
}
