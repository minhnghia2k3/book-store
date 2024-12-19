package com.minhnghia2k3.book.store.domain.dtos;

public class ApiResponseDto<T> {
    private Integer status;
    private String message;
    private T response;

    public ApiResponseDto() {
    }

    public ApiResponseDto(Integer status, String message, T response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    // Builder
    public ApiResponseDto<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ApiResponseDto<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResponseDto<T> setResponse(T response) {
        this.response = response;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getResponse() {
        return response;
    }
}
