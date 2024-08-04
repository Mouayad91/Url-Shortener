package com.url_shortener.app;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.url_shortener.app.dto.UrlDto;
import com.url_shortener.app.entity.UrlEntity;
import com.url_shortener.app.exception.ResourceNotFoundException;
import com.url_shortener.app.repository.UrlRepository;
import com.url_shortener.app.service.impl.UrlServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UrlServiceImplTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlServiceImpl urlService;

    private UrlEntity urlEntity;
    private UrlDto urlDto;

    @BeforeEach
    public void setup() {
        urlEntity = new UrlEntity();
        urlEntity.setId(1L);
        urlEntity.setOriginalUrl("http://google.com");
        urlEntity.setShortUrl("AbCdEf");
        urlEntity.setCreatedAt(LocalDateTime.now());

        urlDto = new UrlDto();
        urlDto.setOriginalUrl("http://google.com");
        urlDto.setTtl("5");
    }

    @Test
    public void testCreateShortUrl() {
        when(urlRepository.findByOriginalUrl(anyString())).thenReturn(null);
        when(urlRepository.save(any(UrlEntity.class))).thenReturn(urlEntity);

        UrlDto createdUrl = urlService.createShortUrl(urlDto);

        assertNotNull(createdUrl);
        assertEquals("http://google.com", createdUrl.getOriginalUrl());
        verify(urlRepository, times(1)).findByOriginalUrl(anyString());
        verify(urlRepository, times(1)).save(any(UrlEntity.class));
    }

    @Test
    public void testDeleteUrlById() {
        when(urlRepository.findById(anyLong())).thenReturn(Optional.of(urlEntity));

        urlService.deleteUrlById(1L);

        verify(urlRepository, times(1)).findById(1L);
        verify(urlRepository, times(1)).delete(urlEntity);
    }

    @Test
    public void testDeleteUrlByNotExistingId() {
        when(urlRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            urlService.deleteUrlById(1L);
        });

        assertEquals("URL not found with id: 1", exception.getMessage());
        verify(urlRepository, times(1)).findById(1L);
        verify(urlRepository, times(0)).delete(any(UrlEntity.class));
    }
}
