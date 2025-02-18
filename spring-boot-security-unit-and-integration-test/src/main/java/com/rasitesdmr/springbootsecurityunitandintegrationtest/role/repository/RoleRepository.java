package com.rasitesdmr.springbootsecurityunitandintegrationtest.role.repository;

import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleName(String roleName);
}
