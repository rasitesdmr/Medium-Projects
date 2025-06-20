package com.rasitesdmr.user_service.repository;


import com.rasitesdmr.user_service.domain.response.UserResponse;
import com.rasitesdmr.user_service.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select new com.rasitesdmr.user_service.domain.response.UserResponse(u.id, u.firstName, u.lastName, u.email, u.createDate, u.updateDate) From User u " +
            "Where u.id = :id ")
    UserResponse findUserResponseById(Long id);

    @Query("SELECT new com.rasitesdmr.user_service.domain.response.UserResponse(u.id, u.firstName, u.lastName, u.email, u.createDate, u.updateDate) FROM User u ")
    Page<UserResponse> findAllUserResponses(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsById(@NotNull Long id);
}
