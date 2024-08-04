package com.url_shortener.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlApiException.class)
    public ResponseEntity<ErrorDetails> handleUrlApiException(UrlApiException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
             exception.getMessage(), 
             request.getDescription(false)
             );

        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(), 
            exception.getMessage(), 
            request.getDescription(false)
            );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(), 
            exception.getMessage(), 
            request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
