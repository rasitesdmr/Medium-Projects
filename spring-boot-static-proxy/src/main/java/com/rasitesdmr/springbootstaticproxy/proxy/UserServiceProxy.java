package com.rasitesdmr.springbootstaticproxy.proxy;

import com.rasitesdmr.springbootstaticproxy.domain.request.UserCreateRequest;
import com.rasitesdmr.springbootstaticproxy.model.User;
import com.rasitesdmr.springbootstaticproxy.service.UserService;

import java.util.UUID;

public class UserServiceProxy implements UserService {

    private final UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User saveUser(User user) {
        try {
            System.out.println("Opening transaction");
            User result = userService.saveUser(user);
            System.out.println("Committing transaction");
            return result;
        } catch (Exception exception) {
            System.out.println("Rollback transaction");
            return null;
        }
    }

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {
        try {
            System.out.println("Opening transaction");
            User result = userService.createUser(userCreateRequest);
            System.out.println("Committing transaction");
            return result;
        } catch (Exception exception) {
            System.out.println("Rollback transaction");
            return null;
        }
    }

    @Override
    public User getUserById(UUID id) {
        try {
            System.out.println("Opening transaction");
            User result = userService.getUserById(id);
            System.out.println("Committing transaction");
            return result;
        } catch (Exception exception) {
            System.out.println("Rollback transaction");
            return null;
        }
    }
}
