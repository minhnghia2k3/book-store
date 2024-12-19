package com.minhnghia2k3.book.store.domain.dtos;

public class RegisterUserDto {
    private String email;
    private String password;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String email, String password) {
        this.email = email;
        this.password = password;
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
}
