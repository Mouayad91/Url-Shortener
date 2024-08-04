package com.url_shortener.app;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.url_shortener.app.dto.UrlDto;
import com.url_shortener.app.entity.UrlEntity;

import com.url_shortener.app.exception.UrlApiException;

import com.url_shortener.app.repository.UrlRepository;
import com.url_shortener.app.service.impl.UrlServiceImpl;


@ExtendWith(MockitoExtension.class)
public class UrlServiceImplTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlServiceImpl urlService;

    private UrlDto urlDto;
    private UrlEntity urlEntity;

    @BeforeEach
    public void setup() {
        urlDto = new UrlDto();
        urlDto.setOriginalUrl("http://google.com");

        urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl("http://google.com");
        urlEntity.setShortUrl("AbCdEf");
        urlEntity.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testCreateShortUrl() {
        when(urlRepository.findByOriginalUrl(anyString())).thenReturn(null);
        when(urlRepository.save(any(UrlEntity.class))).thenReturn(urlEntity);

        UrlDto createdUrl = urlService.createShortUrl(urlDto);

        assertNotNull(createdUrl);
        assertEquals(createdUrl.getShortUrl(), createdUrl.getShortUrl());
        verify(urlRepository, times(1)).findByOriginalUrl(anyString());
        verify(urlRepository, times(1)).save(any(UrlEntity.class));
    }

@Test
    public void testCreateShortUrl_EmptyUrl() {
        urlDto.setOriginalUrl("");

        UrlApiException exception = assertThrows(UrlApiException.class, () -> urlService.createShortUrl(urlDto));
        assertEquals("URL cannot be empty or blank", exception.getMessage());
    }


    @Test
    public void testDeleteExpiredUrls() {
        when(urlRepository.findAllWithTtlEqualsCurrentDate(any(LocalDate.class)))
                .thenReturn(Arrays.asList(urlEntity));

        urlService.deleteExpiredUrls();

        verify(urlRepository, times(1)).findAllWithTtlEqualsCurrentDate(any(LocalDate.class));
        verify(urlRepository, times(1)).delete(any(UrlEntity.class));
    }

}
