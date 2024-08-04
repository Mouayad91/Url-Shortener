package com.url_shortener.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.url_shortener.app.service.UrlService;

/**
 * a scheduled task component to remove expired URLs.
 * 
 */
@Component
public class RemoveExpiredUrl {

    @Autowired
    private UrlService urlService;

    private Logger logger = LoggerFactory.getLogger(RemoveExpiredUrl.class);

    /**
     * Runs every minute to check for expired URLs
     */
    @Scheduled(fixedRate = 60000)
    public void deleteExpiredUrls() {
        logger.info("Deleting expired URLs");
        urlService.deleteExpiredUrls();
    }
}