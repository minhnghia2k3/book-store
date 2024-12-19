package com.minhnghia2k3.book.store.domain.dtos.response;

import com.minhnghia2k3.book.store.domain.entities.RoleEntity;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private boolean isActivated;
    private Date createdAt;
    private Date updatedAt;
    private Set<RoleEntity> roles = new HashSet<>();

    public UserResponse() {
    }

    public UserResponse(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.isActivated = user.isActivated();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.roles = user.getRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isActivated=" + isActivated +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roles=" + roles +
                '}';
    }
}
