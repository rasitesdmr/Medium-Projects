package com.rasitesdmr.user_service.controller;

import com.rasitesdmr.user_service.domain.request.UserCreateRequest;
import com.rasitesdmr.user_service.domain.response.PageResponse;
import com.rasitesdmr.user_service.domain.response.UserDetailsResponse;
import com.rasitesdmr.user_service.domain.response.UserResponse;
import com.rasitesdmr.user_service.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public PageResponse<UserResponse> getAllUserResponses(@Argument(value = "pageNo") int pageNo, @Argument(value = "pageSize") int pageSize){
        return userService.getAllUserResponses(pageNo, pageSize);
    }

    @MutationMapping
    public UserResponse createUser(@Argument(value = "userCreateRequest") UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @QueryMapping
    public UserDetailsResponse getUserDetailsResponseById(@Argument(value = "id") Long id){
        return userService.getUserDetailsResponseById(id);
    }
}
