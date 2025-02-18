package com.rasitesdmr.springbootsecurityunittest.user.repository;

import com.rasitesdmr.springbootsecurityunittest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserName(String username);
    boolean existsByUserName(String username);
}
