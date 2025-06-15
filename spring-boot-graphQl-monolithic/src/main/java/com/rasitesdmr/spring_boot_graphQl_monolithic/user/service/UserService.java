package com.rasitesdmr.spring_boot_graphQl_monolithic.user.service;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.UserCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.UserDetailsResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.UserResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.User;

public interface UserService {
    void saveUser(User user);

    UserResponse createUser(UserCreateRequest request);

    boolean existsByEmail(String email);

    UserResponse getUserResponseById(Long id);

    PageResponse<UserResponse> getAllUserResponses(int pageNo, int pageSize);

    User getById(Long id);

    UserDetailsResponse getUserDetailsResponseById(Long id);
}
