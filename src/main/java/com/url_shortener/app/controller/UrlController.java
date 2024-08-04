package com.url_shortener.app.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.url_shortener.app.dto.UrlDto;
import com.url_shortener.app.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    @Autowired
    private UrlService urlService;

    private Logger logger = LoggerFactory.getLogger(UrlController.class);

    @PostMapping
    public ResponseEntity<UrlDto> generateShortenUrl(@Valid @RequestBody UrlDto urlDto) {
        try {
            UrlDto createdUrl = urlService.createShortUrl(urlDto);
            createdUrl.setShortUrl("http://localhost:8080/api/urls/short/" + createdUrl.getShortUrl());
            return new ResponseEntity<>(createdUrl, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while generating short URL", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/short/{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        try {
            logger.info("Redirecting to original URL for short URL: " + shortUrl);
            String originalUrl = urlService.getOriginalUrl(shortUrl);
            if (originalUrl != null) {
                response.sendRedirect(originalUrl);
            } else {
                response.sendError(HttpStatus.NOT_FOUND.value(), "URL not found");
            }
        } catch (Exception e) {
            logger.error("Something went wrong when fetching original url: ", e);
            response.sendError(HttpStatus.NOT_FOUND.value(), "URL NOT FOUND");
        }
    }

    @DeleteMapping("/short/{shortUrl}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortUrl) {
        try {
            urlService.deleteUrl(shortUrl);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while deleting URL", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UrlDto> getUrlById(@PathVariable Long id) {
        try {
            UrlDto urlDto = urlService.getUrlById(id);
            return new ResponseEntity<>(urlDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching URL by ID", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UrlDto> updateUrl(@RequestBody UrlDto urlDto, @PathVariable Long id) {
        try {
            UrlDto updatedUrlDto = urlService.updateUrl(urlDto, id);
            return new ResponseEntity<>(updatedUrlDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while updating URL", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/expired")
    public ResponseEntity<Void> deleteExpiredUrls() {
        try {
            urlService.deleteExpiredUrls();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while deleting expired URLs", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<UrlDto>> getAllUrls(){

        List<UrlDto> urlDtos = urlService.getAllUrlList();
        return new ResponseEntity<>(urlDtos, HttpStatus.OK);
        
    }
    
    @DeleteMapping("/{id}")
    
    public ResponseEntity<Void> deleteUrlById(@PathVariable("id") Long id) {
        try {
            urlService.deleteUrlById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while deleting URL", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
