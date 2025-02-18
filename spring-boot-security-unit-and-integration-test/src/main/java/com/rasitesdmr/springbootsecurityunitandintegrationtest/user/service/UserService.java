package com.rasitesdmr.springbootsecurityunitandintegrationtest.user.service;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.User;

import java.util.List;


public interface UserService {
    void saveUser(User user);
    void createUser(UserCreateRequest userCreateRequest);
    User getUserByUsername(String username);
    boolean checkExistUserName(String username);
    List<User> getAllUsers();
    User getUserByToken();

}
