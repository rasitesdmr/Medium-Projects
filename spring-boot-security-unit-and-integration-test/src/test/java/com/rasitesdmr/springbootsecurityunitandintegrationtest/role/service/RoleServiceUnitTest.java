package com.rasitesdmr.springbootsecurityunitandintegrationtest.role.service;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.domain.exception.exceptions.NotAvailableException;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleServiceUnitTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    @DisplayName("Method getRoleByName should return role when role name exists")
    void getRoleByName_shouldReturnRole_whenRoleNameExists() {
        UUID roleID = UUID.randomUUID();
        String roleName = "ROLE_USER";
        Date createdDate = new Date();
        Date updatedDate = new Date();

        Role expectedRole = Role.builder()
                .id(roleID)
                .roleName(roleName)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();

        Mockito.when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(expectedRole));
        Role resultRole = roleService.getRoleByName(roleName);

        assertNotNull(resultRole);
        assertEquals(expectedRole, resultRole);
        assertEquals(expectedRole.getId(), resultRole.getId());
        assertEquals(expectedRole.getRoleName(), resultRole.getRoleName());
        assertEquals(expectedRole.getCreatedDate(), resultRole.getCreatedDate());
        assertEquals(expectedRole.getUpdatedDate(), resultRole.getUpdatedDate());
        assertEquals(expectedRole.getUsers(), resultRole.getUsers());

        Mockito.verify(roleRepository).findByRoleName(roleName);
    }
    @Test
    @DisplayName("Method getRoleByName should throw exception when role name does not exist")
    void getRoleByName_shouldThrowException_whenRoleNameDoesNotExist() {
        String roleName = "ROLE_ADMIN";
        Mockito.when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.empty());
        NotAvailableException exception = assertThrows(NotAvailableException.class, () -> roleService.getRoleByName(roleName));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(String.format("Role with the role name %s not found.", roleName), exception.getMessage());

        Mockito.verify(roleRepository).findByRoleName(roleName);
    }


}