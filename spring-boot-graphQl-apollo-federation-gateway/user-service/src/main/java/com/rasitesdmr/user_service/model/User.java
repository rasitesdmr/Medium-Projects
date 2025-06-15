package com.rasitesdmr.user_service.model;


import com.rasitesdmr.user_service.domain.audit.Auditable;
import com.rasitesdmr.user_service.domain.enums.UserAuthRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserAuthRole userAuthRole;

    @Override
    public String toString() {
        return String.format("USER ID : %s, USER EMAIL : %s", this.id, this.email);
    }
}
