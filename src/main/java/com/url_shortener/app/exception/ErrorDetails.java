package com.url_shortener.app.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Encapsulates error details for a given exception.
 */
public class ErrorDetails {
    
    private LocalDateTime timestamp; 
    private String message; 
    private String details;

}