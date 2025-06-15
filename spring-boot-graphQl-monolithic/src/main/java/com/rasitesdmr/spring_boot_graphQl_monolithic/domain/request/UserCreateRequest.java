package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.enums.UserAuthRole;
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
