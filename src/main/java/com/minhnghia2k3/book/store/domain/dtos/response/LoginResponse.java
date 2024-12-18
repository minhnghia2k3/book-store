package com.minhnghia2k3.book.store.domain.dtos.response;

import java.util.Date;

public class LoginResponse {
    private String token;
    private Date expiresIn;

    public LoginResponse() {
    }

    public LoginResponse(String token, Date expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }
}
