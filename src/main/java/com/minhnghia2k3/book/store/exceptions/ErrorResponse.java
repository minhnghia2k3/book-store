package com.minhnghia2k3.book.store.exceptions;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int code;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int code, String message) {
        super();
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
