package com.rasitesdmr.user_service.domain.response;

import com.rasitesdmr.user_service.domain.enums.UserAuthRole;

import java.time.LocalDateTime;


public interface UserResponse {
     Long getId();
     String getFirstName();
     String getLastName();
     String getEmail();
     UserAuthRole getUserAuthRole();
     LocalDateTime getCreateDate();
     LocalDateTime getUpdateDate();
}
