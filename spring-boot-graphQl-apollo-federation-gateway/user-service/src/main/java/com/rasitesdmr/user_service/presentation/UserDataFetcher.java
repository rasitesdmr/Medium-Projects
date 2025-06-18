package com.rasitesdmr.user_service.presentation;

import com.netflix.graphql.dgs.*;
import com.rasitesdmr.user_service.domain.request.UserCreateRequest;
import com.rasitesdmr.user_service.domain.response.PageResponse;
import com.rasitesdmr.user_service.domain.response.UserDetailsResponse;
import com.rasitesdmr.user_service.domain.response.UserResponse;
import com.rasitesdmr.user_service.service.UserService;

import java.util.Map;

@DgsComponent
public class UserDataFetcher {

    private final UserService userService;

    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @DgsMutation
    public UserResponse createUser(@InputArgument("userCreateRequest") UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }


    @DgsQuery
    public PageResponse<UserResponse> getAllUserResponses(
            @InputArgument("pageNo") Integer pageNo,
            @InputArgument("pageSize") Integer pageSize
    ) {
        return userService.getAllUserResponses(pageNo, pageSize);
    }

    @DgsQuery
    public UserResponse getUserResponseById(@InputArgument(value = "id") Long id){
        return userService.getUserResponseById(id);
    }


    @DgsQuery
    public UserDetailsResponse getUserDetailsResponseById(@InputArgument("id") Long id) {
        return userService.getUserDetailsResponseById(id);
    }
}