package com.minhnghia2k3.book.store.exceptions.handlers;

import com.minhnghia2k3.book.store.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;
import java.util.UUID;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({AuthorNotFound.class, BookNotFound.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(Exception e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler({AuthorDeletionException.class, UserExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(Exception e) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler({MissingServletRequestPartException.class, MultipartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(Exception e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInputValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> String.format("%s: %s (rejected value: %s)", error.getField(), error.getDefaultMessage(), error.getRejectedValue())).toList();

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid input", errors);
    }

    /* Security Exceptions */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(Exception exception) {
        ErrorResponse response;
        switch (exception) {
            case BadCredentialsException _ ->
                    response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The username or password is incorrect");
            case AccountStatusException _ ->
                    response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "The account is locked");
            case AccessDeniedException _ ->
                    response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "You are not authorized to access this resource");
            case SignatureException _ -> {
                response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The JWT signature is invalid");
            }
            case ExpiredJwtException _ -> {
                response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The JWT token has expired");
            }
            case MalformedJwtException _ -> {
                response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The JWT is malformed");
            }
            default -> {
                String traceId = UUID.randomUUID().toString();
                logger.error("Trace ID {}: Exception occurred: ", traceId, exception);
                response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected server error. Trace ID: " + traceId);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknownException(Throwable ex) {
        logger.error("Unhandled exception: ", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected server error");
    }
}
