package com.minhnghia2k3.book.store.exceptions;

public class FileEmptyException extends SpringBootFileUploadException {
    public FileEmptyException(String e) {
        super(e);
    }
}
