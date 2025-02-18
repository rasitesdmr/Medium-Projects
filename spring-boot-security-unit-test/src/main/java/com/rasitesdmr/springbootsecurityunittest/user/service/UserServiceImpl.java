package com.rasitesdmr.springbootsecurityunittest.user.service;

import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.AlreadyAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunittest.domain.model.request.user.UserCreateRequest;
import com.rasitesdmr.springbootsecurityunittest.role.Role;
import com.rasitesdmr.springbootsecurityunittest.role.service.RoleService;
import com.rasitesdmr.springbootsecurityunittest.user.User;
import com.rasitesdmr.springbootsecurityunittest.user.repository.UserRepository;
import com.rasitesdmr.springbootsecurityunittest.util.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception exception) {
            LOG.error("User Not Saved : {}", exception.getMessage());
            throw new InternalServerErrorException(String.format("User Not Saved : %s", exception.getMessage()));
        }
    }

    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        String requestFirstName = userCreateRequest.getFirstName();
        String requestLastName = userCreateRequest.getLastName();
        String requestUserName = userCreateRequest.getUserName();
        String requestEmail = userCreateRequest.getEmail();
        String requestPassword = userCreateRequest.getPassword();
        String requestRoleName = userCreateRequest.getRoleName();

        boolean existUsername = checkExistUserName(requestUserName);
        if (existUsername) throw new AlreadyAvailableException(String.format("User with the username %s already available.", requestUserName));
        Role role = roleService.getRoleByName(requestRoleName);
        User user = User.builder()
                .firstName(requestFirstName)
                .lastName(requestLastName)
                .userName(requestUserName)
                .email(requestEmail)
                .password(requestPassword)
                .oldPassword(requestPassword)
                .createdDate(new Date())
                .updatedDate(new Date())
                .role(role)
                .build();

        saveUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new NotAvailableException(String.format("User with the username %s not found.", username)));
    }

    @Override
    public boolean checkExistUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByToken() {
        UUID userID = AuthUtil.getUserIdByToken();
        if (userID == null) return null;
        return userRepository.findById(userID).orElseThrow(() -> new NotAvailableException(String.format("User with the ID %s not found.", userID)));
    }
}
