package com.rasitesdmr;

import com.rasitesdmr.domain.request.UserCreateRequest;
import com.rasitesdmr.model.User;
import com.rasitesdmr.proxy.ProxyFactory;
import com.rasitesdmr.service.UserService;

public class Main {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(Main.class.getPackage());

        UserService userService = proxyFactory.getBean(UserService.class);
        UserCreateRequest request = new UserCreateRequest("rasitesdmr", "test@gmail.com");
        User user = userService.createUser(request);
        User expectedUser = userService.getUserById(user.getId());
        System.out.println("Expected user Id : " + expectedUser.getId());
    }
}