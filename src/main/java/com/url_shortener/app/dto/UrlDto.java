package com.url_shortener.app.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for URL details
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UrlDto {
    private Long id;
   
    @NotBlank(message = "Original URL cannot be empty or blank")
    private String originalUrl;
    
    private String shortUrl;
   
    private LocalDateTime createdAt;
   
    // Time-to-Live (TTL) in days, must be a positive number
   @Min(value = 0, message = "Time-to-Live must be a positive number")
   @Pattern(regexp = "^[0-9]+$", message = "Time-to-Live must be a valid number")
    private String ttl;
}