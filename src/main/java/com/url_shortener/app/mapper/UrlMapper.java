package com.url_shortener.app.mapper;

import java.time.LocalDate;

import com.url_shortener.app.dto.UrlDto;
import com.url_shortener.app.entity.UrlEntity;

public class UrlMapper {

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

    public static UrlDto mapToUrlDto(UrlEntity urlEntity) {

        UrlDto urlDto = new UrlDto(
                urlEntity.getId(),
                urlEntity.getOriginalUrl(),
                urlEntity.getShortUrl(),
                urlEntity.getCreatedAt(),
                urlEntity.getTtl() == null ? null : urlEntity.getTtl().toString());

        return urlDto;

    }

    private static LocalDate setLocalDatetime(String ttl) {
        return ttl == null ? null : LocalDate.now().plusDays(Long.parseLong(ttl));
    }

}