package com.rasitesdmr.springbootsecurityunitandintegrationtest.util;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.security.userdetails.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthUtil {

    public static UUID getUserIdByToken() {
        return getCustomerUserDetails().getUserId();
    }

    public static String getUsernameByToken() {
        return getCustomerUserDetails().getUsername();
    }

    public static String getPasswordByToken() {
        return getCustomerUserDetails().getPassword();
    }

    public static String getRoleNameByToken() {
        return getCustomerUserDetails().getRoleName();
    }

    public static CustomUserDetails getCustomerUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
