package com.rasitesdmr.springbootsecurityunitandintegrationtest.security.userdetails;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.service.RoleService;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.User;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.user.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.getUserByUsername(username);
        UUID userId = user.getId();
        String userName = user.getUserName();
        String password = user.getPassword();
        String roleName = user.getRole().getRoleName();
        return new CustomUserDetails(userId, userName, password, roleName);
    }
}
