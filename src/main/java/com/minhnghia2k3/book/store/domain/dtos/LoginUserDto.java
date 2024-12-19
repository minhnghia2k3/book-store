package com.minhnghia2k3.book.store.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class LoginUserDto {

    @NotEmpty(message = "Email address is required")
    @Email(message = "Email address is invalid", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password is invalid"
    )
    private String password;

    public LoginUserDto() {
    }

    public LoginUserDto(String email, String password) {
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
