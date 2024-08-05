package com.url_shortener.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url_shortener.app.dto.UrlDto;
import com.url_shortener.app.exception.DuplicateUrlException;
import com.url_shortener.app.exception.ResourceNotFoundException;
import com.url_shortener.app.exception.UrlApiException;
import com.url_shortener.app.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * Rest controller for handling URL requests
 */

@RestController 
@RequestMapping("/")
public class UrlController {
    
    @Autowired // Injecting the UrlService bean
    private UrlService urlService;

    // Logger for logging messages
    private Logger logger = LoggerFactory.getLogger(UrlController.class);


    /**
     * Create a short URL from original URL
     * @param urlDto
     * @return ResponseEntity with created URL or error message if something went wrong
     */
    @PostMapping("")
    public ResponseEntity<?> generateShortenUrl(@Valid @RequestBody UrlDto urlDto) {
             try {
            UrlDto createdUrl = urlService.createShortUrl(urlDto);
            createdUrl.setShortUrl("http://localhost:8080/" + createdUrl.getShortUrl());
            return new ResponseEntity<>(createdUrl, HttpStatus.CREATED);
        } catch (DuplicateUrlException e) {
            logger.error("Duplicated original URL", e);
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (UrlApiException e) {
            logger.error("Error while generating short URL", e);
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error while generating short URL", e);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Unexpected error occurred");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Redirect to the original URL
     * @param shortUrl
     * @param response
     * @throws IOException
     */

    @GetMapping("{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
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

    /**
     * Delete a short URL
     * @param shortUrl
     * @return
     */
    @DeleteMapping("{shortUrl}")
    public ResponseEntity<Void> deleteUrl(@PathVariable("shortUrl") String shortUrl) {
        try {
            urlService.deleteUrl(shortUrl);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while deleting URL", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Get URL details by ID
     * @param id
     * @return URL details
     */
    @GetMapping("id/{id}")
    public ResponseEntity<UrlDto> getUrlById(@PathVariable("id") Long id) {
        try {
            UrlDto urlDto = urlService.getUrlById(id);
            return new ResponseEntity<>(urlDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching URL by ID", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     *  Update Url original URL and ttl by ID
     * @param urlDto
     * @param id
     * @return URL details
     */
    @PutMapping("{id}")
public ResponseEntity<?> updateUrl(@RequestBody UrlDto urlDto, @PathVariable("id") Long id) {
    try {
        UrlDto updatedUrlDto = urlService.updateUrl(urlDto, id);
        return new ResponseEntity<>(updatedUrlDto, HttpStatus.OK);
    } 
    catch (ResourceNotFoundException e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } 
    catch (UrlApiException e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    } 
    catch (Exception e) {
        logger.error("Something went wrong while updating URL", e);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Something went wrong");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    /**
     * Delete all expired URLs
     * @return Http status of the deletion operation result
     */
    @DeleteMapping("expired")
    public ResponseEntity<Void> deleteExpiredUrls() {
        try {
            urlService.deleteExpiredUrls();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Something went wrong while deleting expired URLs", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all URLs details from database
     * @return list of all URLs
     */
    @GetMapping
    public ResponseEntity<List<UrlDto>> getAllUrls(){

        List<UrlDto> urlDtos = urlService.getAllUrlList();
        return new ResponseEntity<>(urlDtos, HttpStatus.OK);
        
    }

    /**
     * Delete URL by ID
     * @param id
     * @return Http status of the deletion operation result
     */
    
   @DeleteMapping("{id}")
public ResponseEntity<Map<String, String>> deleteUrlById(@PathVariable("id") Long id) {
    try {
        urlService.deleteUrlById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "URL with the id: " + id + " is deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 
    catch (ResourceNotFoundException e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } 
    catch (Exception e) {
        logger.error("Error while deleting URL", e);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Something went wrong while deleting the URL");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
