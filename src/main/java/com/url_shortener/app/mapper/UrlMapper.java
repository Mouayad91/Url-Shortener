package com.url_shortener.app.mapper;

import java.time.LocalDate;

import com.url_shortener.app.dto.UrlDto;
import com.url_shortener.app.entity.UrlEntity;

/**
 * Mapper to convert between UrlDto and UrlEntity objects.
 */
public class UrlMapper {
    /**
     * Convert UrlDto to UrlEntity.
     * @param urlDto
     * @return UrlEntity
     */
    public static UrlEntity mapToUrlEntity(UrlDto urlDto) {

        UrlEntity urlEntity = new UrlEntity(
                urlDto.getId(),
                urlDto.getOriginalUrl(),
                urlDto.getShortUrl(),
                urlDto.getCreatedAt(),
                setLocalDatetime(urlDto.getTtl())

        );

        return urlEntity;
    }
    /**
     * Convert UrlEntity to UrlDto.
     * @param urlEntity
     * @return UrlDto
     */
    public static UrlDto mapToUrlDto(UrlEntity urlEntity) {

        UrlDto urlDto = new UrlDto(
                urlEntity.getId(),
                urlEntity.getOriginalUrl(),
                urlEntity.getShortUrl(),
                urlEntity.getCreatedAt(),
                urlEntity.getTtl() == null ? null : urlEntity.getTtl().toString());

        return urlDto;

    }
    /**
     * Convert String TTL to LocalDate.
     * @param ttl
     * @return LocalDate based on TTL string.
     */
    private static LocalDate setLocalDatetime(String ttl) {
        return ttl == null ? null : LocalDate.now().plusDays(Long.parseLong(ttl));
    }

}