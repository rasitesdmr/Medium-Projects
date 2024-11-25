package com.rasitesdemir.spring_boot_cqs.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;
}
