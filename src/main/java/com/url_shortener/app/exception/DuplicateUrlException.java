package com.url_shortener.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUrlException extends RuntimeException  {
    

    public DuplicateUrlException(String message) {
        super(message);
    }


}
