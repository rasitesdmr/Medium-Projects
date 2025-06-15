package com.rasitesdmr.user_service.domain.request;

import com.rasitesdmr.user_service.domain.enums.UserAuthRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserAuthRole userAuthRole;
}
