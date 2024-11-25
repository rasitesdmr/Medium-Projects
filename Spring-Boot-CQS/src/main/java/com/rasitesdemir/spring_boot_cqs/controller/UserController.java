package com.rasitesdemir.spring_boot_cqs.controller;

import com.rasitesdemir.spring_boot_cqs.command.CommandService;
import com.rasitesdemir.spring_boot_cqs.model.User;
import com.rasitesdemir.spring_boot_cqs.model.request.UserRequest;
import com.rasitesdemir.spring_boot_cqs.query.QueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final CommandService commandService;
    private final QueryService queryService;

    public UserController(CommandService commandService, QueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping(path = "")
    public void createUser(@RequestBody UserRequest userRequest){
        commandService.createUser(userRequest);
    }

    @DeleteMapping(path = "/{userID}")
    public void deleteUser(@PathVariable UUID userID){
        commandService.deleteUser(userID);
    }

    @PutMapping(path = "/{userID}")
    public void updateUser(@PathVariable UUID userID, @RequestBody UserRequest userRequest){
        commandService.updateUser(userID, userRequest);
    }

    @GetMapping(path = "/{userID}")
    public User getUserById(@PathVariable UUID userID){
        return queryService.getUserById(userID);
    }

    @GetMapping(path = "/all")
    public List<User> getAllUser(){
        return queryService.getAllUser();
    }
}
