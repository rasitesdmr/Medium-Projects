package com.rasitesdmr.springbootsecurityunittest.role.service;


import com.rasitesdmr.springbootsecurityunittest.role.Role;

public interface RoleService {
    Role getRoleByName(String roleName);
}
