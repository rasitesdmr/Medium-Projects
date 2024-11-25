package com.rasitesdemir.spring_boot_cqs.command;

import com.rasitesdemir.spring_boot_cqs.model.User;
import com.rasitesdemir.spring_boot_cqs.model.request.UserRequest;

import java.util.UUID;

public interface CommandService {

    void saveUser(User user);

    void createUser(UserRequest userRequest);

    void deleteUser(UUID userID);

    void updateUser(UUID userID, UserRequest userRequest);
}
