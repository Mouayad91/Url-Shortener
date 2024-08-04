package com.url_shortener.app.service;

import java.util.List;

import com.url_shortener.app.dto.UrlDto;


// Service Interface for URL service operations
public interface UrlService {
    UrlDto createShortUrl(UrlDto urlDto);
    String getOriginalUrl(String shortUrl);
    void deleteExpiredUrls();
    void deleteUrl(String shortUrl);

    UrlDto getUrlById(Long id);
    UrlDto updateUrl(UrlDto urlDto, Long id);
    void deleteUrlById(Long id);
    List<UrlDto> getAllUrlList();
}
