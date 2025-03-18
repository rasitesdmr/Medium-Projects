package com.rasitesdmr.springbootstaticproxy;

import com.rasitesdmr.springbootstaticproxy.domain.request.UserCreateRequest;
import com.rasitesdmr.springbootstaticproxy.model.User;
import com.rasitesdmr.springbootstaticproxy.proxy.UserServiceProxy;
import com.rasitesdmr.springbootstaticproxy.repository.UserRepository;
import com.rasitesdmr.springbootstaticproxy.service.UserService;
import com.rasitesdmr.springbootstaticproxy.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();
        UserService realService = new UserServiceImpl(userRepository);

        UserService proxyService = new UserServiceProxy(realService);

        UserCreateRequest request = new UserCreateRequest("rasitesdmr", "test@gmail.com");
        User user = proxyService.createUser(request);
        System.out.println("User Model : " + user.toString());
    }
}
