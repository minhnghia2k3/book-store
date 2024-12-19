package com.minhnghia2k3.book.store.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInputValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid input", errors);
    }


    @ExceptionHandler(Exception.class)
    public ErrorResponse handleSecurityException(Exception exception) {
        switch (exception) {
            case BadCredentialsException _ -> {
                return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The username or password is incorrect");
            }
            case AccountStatusException _ -> {
                return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "The account is locked");
            }
            case AccessDeniedException _ -> {
                return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "You are not authorized to access this resource");
            }
            case SignatureException _ -> {
                return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The JWT signature is invalid");
            }
            case ExpiredJwtException _ -> {
                return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The JWT token has expired");
            }
            case MalformedJwtException _ -> {
                return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The JWT is malformed");
            }
            default -> {
            }
        }

        // TODO send this stack trace to an observability tool
        exception.printStackTrace();
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Internal server error");
    }
}
