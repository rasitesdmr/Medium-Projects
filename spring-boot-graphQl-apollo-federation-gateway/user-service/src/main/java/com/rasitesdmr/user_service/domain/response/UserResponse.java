package com.rasitesdmr.user_service.domain.response;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserResponse {
     private Long id;
     private String firstName;
     private String lastName;
     private String email;
     private LocalDateTime  createDate;
     private LocalDateTime  updateDate;

     public UserResponse(Long id, String firstName, String lastName, String email, LocalDateTime  createDate, LocalDateTime  updateDate) {
          this.id = id;
          this.firstName = firstName;
          this.lastName = lastName;
          this.email = email;
          this.createDate = createDate;
          this.updateDate = updateDate;
     }
}
