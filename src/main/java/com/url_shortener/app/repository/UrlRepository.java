package com.url_shortener.app.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.url_shortener.app.entity.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>{
    boolean existsByShortUrl(String shortUrl);
    UrlEntity findByShortUrl(String shortUrl);
    void deleteByTtlBefore(LocalDateTime now);
    UrlEntity findByOriginalUrl(String url);
    
    @Query("SELECT u FROM UrlEntity u WHERE u.ttl = :currentDate")
    List<UrlEntity> findAllWithTtlEqualsCurrentDate(@Param("currentDate") LocalDate currentDate);
}
