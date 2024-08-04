package com.url_shortener.app.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
   
    @Min(value = 0, message = "TTL must be a positive number")
    private String ttl;
}
