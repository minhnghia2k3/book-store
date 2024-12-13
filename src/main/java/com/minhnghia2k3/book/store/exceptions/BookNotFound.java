package com.minhnghia2k3.book.store.exceptions;

public class BookNotFound extends RuntimeException {
    public BookNotFound(String message) {
        super(message);
    }
}
