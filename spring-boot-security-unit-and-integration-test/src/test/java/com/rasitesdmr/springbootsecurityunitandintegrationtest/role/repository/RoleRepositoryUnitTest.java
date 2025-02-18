package com.rasitesdmr.springbootsecurityunitandintegrationtest.role.repository;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryUnitTest {

    @InjectMocks
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
    }

    @Test
    @DisplayName("Method findByRoleName should return role when role name exist")
    void findByRoleName_shouldReturnRole_whenRoleNameExist(){
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
        Optional<Role> result = roleRepository.findByRoleName(roleName);

        assertTrue(result.isPresent());
        assertEquals(expectedRole.getId(), result.get().getId());
        assertEquals(expectedRole.getRoleName(), result.get().getRoleName());
    }

    @Test
    @DisplayName("Method findByRoleName should return empty when role name does not exist")
    void findByRoleName_shouldReturnEmpty_whenRoleNameDoesNotExist(){
        String roleName = "ROLE_USER";

        Mockito.when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.empty());

        Optional<Role> result = roleRepository.findByRoleName(roleName);

        assertTrue(result.isEmpty());
    }


}