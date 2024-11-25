package com.rasitesdemir.spring_boot_cqs.query;

import com.rasitesdemir.spring_boot_cqs.model.User;

import java.util.List;
import java.util.UUID;

public interface QueryService {

    User getUserById(UUID userID);

    List<User> getAllUser();
}
