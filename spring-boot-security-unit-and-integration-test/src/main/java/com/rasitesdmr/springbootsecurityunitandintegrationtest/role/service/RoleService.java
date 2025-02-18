package com.rasitesdmr.springbootsecurityunitandintegrationtest.role.service;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;

public interface RoleService {
    Role getRoleByName(String roleName);
}
