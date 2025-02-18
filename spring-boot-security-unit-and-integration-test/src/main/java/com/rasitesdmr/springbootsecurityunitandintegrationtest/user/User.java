package com.rasitesdmr.springbootsecurityunitandintegrationtest.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rasitesdmr.springbootsecurityunitandintegrationtest.role.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String oldPassword;
    private Date createdDate;
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    public User() {
    }

    public User(String firstName, String lastName, String userName, String email, String password, String oldPassword, Date createdDate, Date updatedDate, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.oldPassword = oldPassword;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.role = role;
    }

    public User(UUID id, String firstName, String lastName, String userName, String email, String password, String oldPassword, Date createdDate, Date updatedDate, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.oldPassword = oldPassword;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String firstName;
        private String lastName;
        private String userName;
        private String email;
        private String password;
        private String oldPassword;
        private Date createdDate;
        private Date updatedDate;
        private Role role;

        public UserBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder userName(final String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public UserBuilder oldPassword(final String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        public UserBuilder createdDate(final Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public UserBuilder updatedDate(final Date updatedDate) {
            this.updatedDate = updatedDate;
            return this;
        }

        public UserBuilder role(final Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(firstName, lastName, userName, email, password, oldPassword, createdDate, updatedDate, role);
        }
    }
}
