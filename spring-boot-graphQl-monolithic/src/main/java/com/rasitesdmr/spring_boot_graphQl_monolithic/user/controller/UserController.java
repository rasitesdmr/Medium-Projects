package com.rasitesdmr.spring_boot_graphQl_monolithic.user.controller;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.UserCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.UserDetailsResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.UserResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.service.UserService;
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
