package com.rasitesdmr.user_service.service;


import com.rasitesdmr.user_service.domain.request.UserCreateRequest;
import com.rasitesdmr.user_service.domain.response.PageResponse;
import com.rasitesdmr.user_service.domain.response.UserDetailsResponse;
import com.rasitesdmr.user_service.domain.response.UserResponse;
import com.rasitesdmr.user_service.model.User;

public interface UserService {
    void saveUser(User user);

    UserResponse createUser(UserCreateRequest request);

    boolean existsByEmail(String email);

    UserResponse getUserResponseById(Long id);

    PageResponse<UserResponse> getAllUserResponses(int pageNo, int pageSize);

    User getById(Long id);

    UserDetailsResponse getUserDetailsResponseById(Long id);
}
