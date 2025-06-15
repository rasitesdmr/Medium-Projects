package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.enums.UserAuthRole;

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
