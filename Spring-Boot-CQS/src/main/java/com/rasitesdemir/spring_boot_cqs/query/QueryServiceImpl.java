package com.rasitesdemir.spring_boot_cqs.query;

import com.rasitesdemir.spring_boot_cqs.command.CommandServiceImpl;
import com.rasitesdemir.spring_boot_cqs.model.User;
import com.rasitesdemir.spring_boot_cqs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QueryServiceImpl implements QueryService {

    private final UserRepository userRepository;

    public QueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(UUID userID) {
        return userRepository.findById(userID).orElseThrow();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
