package com.rasitesdmr.springbootsecurityunittest.role.service;

import com.rasitesdmr.springbootsecurityunittest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunittest.role.Role;
import com.rasitesdmr.springbootsecurityunittest.role.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new NotAvailableException(String.format("Role with the role name %s not found.", roleName)));
    }
}
