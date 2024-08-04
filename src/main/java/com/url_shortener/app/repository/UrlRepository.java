package com.url_shortener.app.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.url_shortener.app.entity.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>{
    
    boolean existsByShortUrl(String shortUrl); //check if a short url exists
   
    UrlEntity findByShortUrl(String shortUrl); //find a url by its short
    
    void deleteByTtlBefore(LocalDateTime now); // delete url entity when ttl is before current date
   
    UrlEntity findByOriginalUrl(String url); //find a url by its original url
    
    
    // find all url entities with ttl equals to current date 
    @Query("SELECT u FROM UrlEntity u WHERE u.ttl = :currentDate")
    List<UrlEntity> findAllWithTtlEqualsCurrentDate(@Param("currentDate") LocalDate currentDate);
}
