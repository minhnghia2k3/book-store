package com.minhnghia2k3.book.store.domain.entities;

public class UserEntityBuilder {
    private String username;
    private String email;
    private String password;

    public UserEntityBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntityBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntityBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntity createUserEntity() {
        return new UserEntity(username, email, password);
    }
}