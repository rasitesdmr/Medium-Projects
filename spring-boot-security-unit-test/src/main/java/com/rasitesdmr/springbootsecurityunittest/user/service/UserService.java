package com.rasitesdmr.springbootsecurityunittest.user.service;

import com.rasitesdmr.springbootsecurityunittest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunittest.user.User;

import java.util.List;


public interface UserService {
    void saveUser(User user);
    void createUser(UserCreateRequest userCreateRequest);
    User getUserByUsername(String username);
    boolean checkExistUserName(String username);
    List<User> getAllUsers();
    User getUserByToken();

}
