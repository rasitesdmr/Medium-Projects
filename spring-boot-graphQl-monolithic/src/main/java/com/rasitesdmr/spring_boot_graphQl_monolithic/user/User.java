package com.rasitesdmr.spring_boot_graphQl_monolithic.user;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.audit.Auditable;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.enums.UserAuthRole;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.Like;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.Photo;
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes;

    @Override
    public String toString() {
        return String.format("USER ID : %s, USER EMAIL : %s", this.id, this.email);
    }
}
