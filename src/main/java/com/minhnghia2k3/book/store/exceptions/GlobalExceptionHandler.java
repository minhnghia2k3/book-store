package com.minhnghia2k3.book.store.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({AuthorNotFound.class, BookNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(Exception e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler({AuthorDeletionException.class, UserExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(Exception e) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        switch (exception) {
            case BadCredentialsException badCredentialsException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
                errorDetail.setProperty("description", "The username or password is incorrect");

                return errorDetail;
            }
            case AccountStatusException accountStatusException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "The account is locked");
                return errorDetail;
            }
            case AccessDeniedException accessDeniedException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "You are not authorized to access this resource");
                return errorDetail;
            }
            case SignatureException signatureException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "The JWT signature is invalid");
                return errorDetail;
            }
            case ExpiredJwtException expiredJwtException -> {
                errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
                errorDetail.setProperty("description", "The JWT token has expired");
                return errorDetail;
            }
            default -> {
            }
        }

        // TODO send this stack trace to an observability tool
        exception.printStackTrace();
        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
        errorDetail.setProperty("description", "Unknown internal server error.");

        return errorDetail;
    }
}
