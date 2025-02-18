package com.rasitesdmr.springbootsecurityunittest.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rasitesdmr.springbootsecurityunittest.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;
    private String roleName;
    private Date createdDate;
    private Date updatedDate;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> users;

    public Role() {
    }

    public Role(UUID id, String roleName, Date createdDate, Date updatedDate) {
        this.id = id;
        this.roleName = roleName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Role(String roleName, Date createdDate, Date updatedDate, List<User> users) {
        this.roleName = roleName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.users = users;
    }

    public Role(UUID id, String roleName, Date createdDate, Date updatedDate, List<User> users) {
        this.id = id;
        this.roleName = roleName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public static RoleBuilder builder() {
        return new RoleBuilder();
    }

    public static class RoleBuilder {
        private UUID id;
        private String roleName;
        private Date createdDate;
        private Date updatedDate;
        private List<User> users;

        public RoleBuilder id(final UUID id) {
            this.id = id;
            return this;
        }

        public RoleBuilder roleName(final String roleName) {
            this.roleName = roleName;
            return this;
        }

        public RoleBuilder createdDate(final Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public RoleBuilder updatedDate(final Date updatedDate) {
            this.updatedDate = updatedDate;
            return this;
        }

        public RoleBuilder users(final List<User> users) {
            this.users = users;
            return this;
        }

        public Role build() {
            return new Role(id, roleName, createdDate, updatedDate, users);
        }
    }
}
