package com.minhnghia2k3.book.store.exceptions.handlers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.minhnghia2k3.book.store.exceptions.ErrorResponse;
import com.minhnghia2k3.book.store.exceptions.FileDownloadException;
import com.minhnghia2k3.book.store.exceptions.FileEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class FileUploadExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileEmptyException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected ErrorResponse handleFileEmptyException(FileEmptyException e) {
        return new ErrorResponse(HttpStatus.NO_CONTENT.value(), e.getMessage());
    }

    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleFileDownloadException(FileDownloadException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    // Handle request successfully processed, but AWS S3 couldn't process => return error
    @ExceptionHandler(AmazonServiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ErrorResponse handleAmazonServiceException(AmazonServiceException e) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(SdkClientException.class)
    protected ErrorResponse handleSdkClientException(RuntimeException ex) {
        String bodyOfResponse = ex.getMessage();
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), bodyOfResponse);
    }

    @ExceptionHandler({IOException.class, FileNotFoundException.class, MultipartException.class})
    protected ErrorResponse handleIOException(Exception ex) {
        String bodyOfResponse = ex.getMessage();
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), bodyOfResponse);
    }
}
