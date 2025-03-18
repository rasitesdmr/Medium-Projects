package com.rasitesdmr.service;

import com.rasitesdmr.annotation.Service;
import com.rasitesdmr.annotation.Transaction;
import com.rasitesdmr.domain.request.UserCreateRequest;
import com.rasitesdmr.model.User;
import com.rasitesdmr.repository.UserRepository;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transaction
    public User saveUser(User user) {
        user = userRepository.save(user);
        System.out.println("saveUser method end.");
        //throw new RuntimeException("SaveUser method error.");
        return user;
    }

    @Override
    @Transaction
    public User createUser(UserCreateRequest userCreateRequest) {
        System.out.println("createUser method started... ");
        final String requestUsername = userCreateRequest.getUsername();
        final String requestEmail = userCreateRequest.getEmail();
        User user = new User(requestUsername, requestEmail);
        return saveUser(user);
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id);
    }
}