package com.rasitesdmr.spring_boot_graphQl_monolithic.user.repository;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.UserResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u From User u " +
            "Where u.id = :id ")
    UserResponse findUserResponseById(Long id);

    @Query("SELECT u FROM User u")
    Page<UserResponse> findAllUserResponses(Pageable pageable);

    boolean existsByEmail(String email);
}
