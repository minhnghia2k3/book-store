package com.minhnghia2k3.book.store.exceptions;

public class AuthorNotFound extends RuntimeException{
    public AuthorNotFound(String message) {
        super(message);
    }
}
