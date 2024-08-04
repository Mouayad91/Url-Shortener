package com.url_shortener.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Custom exception for when a requested resource is not found.
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends RuntimeException{
   
   
    public ResourceNotFoundException(String message){
        super(message);
    }
    
}
