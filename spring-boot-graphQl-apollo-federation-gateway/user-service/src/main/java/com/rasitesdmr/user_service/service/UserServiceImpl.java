package com.rasitesdmr.user_service.service;

import com.rasitesdmr.user_service.domain.enums.UserAuthRole;
import com.rasitesdmr.user_service.domain.exception.exceptions.ConflictException;
import com.rasitesdmr.user_service.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.user_service.domain.exception.exceptions.NotFoundException;
import com.rasitesdmr.user_service.domain.request.UserCreateRequest;
import com.rasitesdmr.user_service.domain.response.PageResponse;
import com.rasitesdmr.user_service.domain.response.UserDetailsResponse;
import com.rasitesdmr.user_service.domain.response.UserResponse;
import com.rasitesdmr.user_service.model.User;
import com.rasitesdmr.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        try {
            userRepository.save(user);
            logger.info("User registered successfully. [{}]", user.toString());
        } catch (Exception exception) {
            logger.error("User registration failed. [{}]", exception.getMessage());
            throw new InternalServerErrorException("User registration failed.");
        }
    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        final String requestFirstName = request.getFirstName();
        final String requestLastName = request.getLastName();
        final String requestEmail = request.getEmail();
        final String requestPassword = request.getPassword();
        final UserAuthRole requestUserAuthRole = request.getUserAuthRole();
        boolean emailExists = existsByEmail(requestEmail);
        if (emailExists) throw new ConflictException("User email already available");
        User user = User.builder()
                .firstName(requestFirstName)
                .lastName(requestLastName)
                .email(requestEmail)
                .password(requestPassword)
                .userAuthRole(requestUserAuthRole)
                .build();

        saveUser(user);
        return getUserResponseById(user.getId());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponse getUserResponseById(Long id) {
        return userRepository.findUserResponseById(id);
    }

    @Override
    public PageResponse<UserResponse> getAllUserResponses(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<UserResponse> page = userRepository.findAllUserResponses(paging);
        return new PageResponse<>(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
