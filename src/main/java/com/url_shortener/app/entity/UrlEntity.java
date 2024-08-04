package com.url_shortener.app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "urls")

//Entity for storing URL data in the database.
public class UrlEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="original_url",nullable = false, unique = true)

    private String originalUrl;
    @Column(name="short_url",nullable = false, unique = true)
    private String shortUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name="ttl")
    private LocalDate ttl;
}