package com.url_shortener.app.exception;

import org.springframework.http.HttpStatus;


import lombok.Getter;


@Getter

public class UrlApiException extends RuntimeException {
    
    private HttpStatus status;

    public UrlApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
