package com.rasitesdemir.spring_boot_cqs.command;

import com.rasitesdemir.spring_boot_cqs.model.User;
import com.rasitesdemir.spring_boot_cqs.model.request.UserRequest;
import com.rasitesdemir.spring_boot_cqs.query.QueryService;
import com.rasitesdemir.spring_boot_cqs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommandServiceImpl implements CommandService {

    private final Logger LOG = LoggerFactory.getLogger(CommandServiceImpl.class);
    private final UserRepository userRepository;
    private final QueryService queryService;

    public CommandServiceImpl(UserRepository userRepository, QueryService queryService) {
        this.userRepository = userRepository;
        this.queryService = queryService;
    }

    @Override
    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception exception) {
            LOG.error("Save User Error : {}", exception.getMessage());
        }
    }

    @Override
    public void createUser(UserRequest userRequest) {
        String requestFirstName = userRequest.getFirstName();
        String requestLastName = userRequest.getLastName();
        String requestUserName = userRequest.getUsername();
        String requestEmail = userRequest.getEmail();
        String requestPassword = userRequest.getPassword();

        User user = User.builder()
                .firstName(requestFirstName)
                .lastName(requestLastName)
                .username(requestUserName)
                .email(requestEmail)
                .password(requestPassword)
                .build();
        saveUser(user);
    }

    @Override
    public void deleteUser(UUID userID) {
        userRepository.deleteById(userID);
    }

    @Override
    public void updateUser(UUID userID, UserRequest userRequest) {
        User user = queryService.getUserById(userID);

        String requestFirstName = userRequest.getFirstName();
        String requestLastName = userRequest.getLastName();
        String requestUserName = userRequest.getUsername();
        String requestEmail = userRequest.getEmail();
        String requestPassword = userRequest.getPassword();

        user.setFirstName(requestFirstName);
        user.setLastName(requestLastName);
        user.setUsername(requestUserName);
        user.setEmail(requestEmail);
        user.setPassword(requestPassword);
        saveUser(user);
    }
}
