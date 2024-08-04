package com.url_shortener.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.url_shortener.app.dto.UrlDto;
import com.url_shortener.app.entity.UrlEntity;
import com.url_shortener.app.exception.ResourceNotFoundException;
import com.url_shortener.app.exception.UrlApiException;
import com.url_shortener.app.mapper.UrlMapper;
import com.url_shortener.app.repository.UrlRepository;
import com.url_shortener.app.service.UrlService;
import com.url_shortener.app.util.UrlUtils;

import jakarta.transaction.Transactional;


@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Override
    public UrlDto createShortUrl(UrlDto urlDto) {
        logger.info("Creating short URL for: " + urlDto.getOriginalUrl());
      
        if (urlDto.getOriginalUrl() == null || urlDto.getOriginalUrl().trim().isEmpty()) {
            throw new UrlApiException(HttpStatus.BAD_REQUEST, "URL cannot be empty or blank");
        }
    
        String shortUrl = UrlUtils.generateShortUrl();

        UrlEntity existing = urlRepository.findByOriginalUrl(urlDto.getOriginalUrl());

        if(existing != null) {
            return UrlMapper.mapToUrlDto(existing);
        }

        UrlEntity urlEntity = UrlMapper.mapToUrlEntity(urlDto);
        urlEntity.setShortUrl(shortUrl);
        urlEntity.setCreatedAt(LocalDateTime.now());
       
        urlEntity = urlRepository.save(urlEntity);

        UrlDto createdUrlDto = UrlMapper.mapToUrlDto(urlEntity);
        createdUrlDto.setShortUrl(shortUrl);
        return createdUrlDto; 
    }


    @Override
    public String getOriginalUrl(String shortUrl) {
        logger.info("Getting original for short url: " + shortUrl);

        if (shortUrl == null || shortUrl.isBlank()) {
            throw new ResourceNotFoundException("Short URL not found");
        }

        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl);

        if (urlEntity == null) {
            throw new ResourceNotFoundException("URL not found or expired");
        }

        return urlEntity.getOriginalUrl();   
    
    }


    @Override
    @Transactional
    public void deleteExpiredUrls() {

        List<UrlEntity> expiredUrls = urlRepository.findAllWithTtlEqualsCurrentDate(LocalDate.now());

        for(UrlEntity urlEntity : expiredUrls) {
            logger.info("Deleting expired URL: " + urlEntity.getShortUrl());
            urlRepository.delete(urlEntity);
        }

    }


    @Override
    @Transactional
    public void deleteUrl(String shortUrl) {
        logger.info("Deleting URL: " + shortUrl);
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl);
        if(urlEntity != null) {
            urlRepository.delete(urlEntity);
        }else {
            throw new ResourceNotFoundException("Short URL not found");
        }
    }


    @Override
    public UrlDto getUrlById(Long id) {
        
        UrlEntity url = urlRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("URL not found"));
      
        return UrlMapper.mapToUrlDto(url);
    }


    @Override
public UrlDto updateUrl(UrlDto urlDto, Long id) {
    UrlEntity urlEntity = urlRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("URL not found"));

    // Validate URL format
    if (urlDto.getOriginalUrl() == null || urlDto.getOriginalUrl().trim().isEmpty()) {
        throw new UrlApiException(HttpStatus.BAD_REQUEST, "Original URL cannot be empty or blank");
    }

    // Convert TTL from String to Integer and validate
    Integer ttlDays;
    try {
        ttlDays = Integer.parseInt(urlDto.getTtl());
        if (ttlDays < 0) {
            throw new UrlApiException(HttpStatus.BAD_REQUEST, "TTL must be a positive number");
        }
    } catch (NumberFormatException e) {
        throw new UrlApiException(HttpStatus.BAD_REQUEST, "TTL must be a valid number");
    }

    // Calculate TTL expiration date based on the number of days
    LocalDate ttlExpirationDate = LocalDate.now().plusDays(ttlDays);

    urlEntity.setOriginalUrl(urlDto.getOriginalUrl());
    urlEntity.setTtl(ttlExpirationDate);

    UrlEntity updatedUrl = urlRepository.save(urlEntity);

    return UrlMapper.mapToUrlDto(updatedUrl);
}


    @Override
    public void deleteUrlById(Long id) {
        
        UrlEntity urlEntity = urlRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException("URL not found"));

        urlRepository.delete(urlEntity);


    }


    @Override
    public List<UrlDto> getAllUrlList() {
        

        List<UrlEntity> urlEntities = urlRepository.findAll();
        return 

        urlEntities.stream()
        .map((url)->UrlMapper.mapToUrlDto(url))
        .collect(Collectors.toList());


    }

}
