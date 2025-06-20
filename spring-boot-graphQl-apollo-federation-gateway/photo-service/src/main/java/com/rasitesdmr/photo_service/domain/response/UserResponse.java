package com.rasitesdmr.photo_service.domain.response;

import lombok.*;

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
     private Date createDate;
     private Date updateDate;

     public UserResponse(Long id, String firstName, String lastName, String email, Date createDate, Date updateDate) {
          this.id = id;
          this.firstName = firstName;
          this.lastName = lastName;
          this.email = email;
          this.createDate = createDate;
          this.updateDate = updateDate;
     }
}
