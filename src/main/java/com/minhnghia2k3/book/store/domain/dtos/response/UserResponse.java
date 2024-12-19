package com.minhnghia2k3.book.store.domain.dtos.response;

import com.minhnghia2k3.book.store.domain.entities.RoleEntity;
import com.minhnghia2k3.book.store.domain.entities.UserEntity;

import java.util.Date;

public class UserResponse {
    private Long id;
    private String email;
    private boolean isActivated;
    private Date createdAt;
    private Date updatedAt;
    private RoleEntity role;

    public UserResponse() {
    }

    public UserResponse(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.isActivated = user.isActivated();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", isActivated=" + isActivated +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role=" + role +
                '}';
    }
}
