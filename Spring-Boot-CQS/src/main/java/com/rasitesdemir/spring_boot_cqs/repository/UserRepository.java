package com.rasitesdemir.spring_boot_cqs.repository;

import com.rasitesdemir.spring_boot_cqs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
