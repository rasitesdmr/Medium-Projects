package com.rasitesdmr.repository;

import com.rasitesdmr.model.User;

import java.util.UUID;

public interface JpaRepository {
    User save(User user);
    User findById(UUID id);
}
