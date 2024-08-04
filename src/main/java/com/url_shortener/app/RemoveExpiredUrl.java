package com.url_shortener.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.url_shortener.app.service.UrlService;


@Component
public class RemoveExpiredUrl {

    @Autowired
    private UrlService urlService;

    private Logger logger = LoggerFactory.getLogger(RemoveExpiredUrl.class);

    @Scheduled(fixedRate = 60000)
    public void deleteExpiredUrls() {
        logger.info("Deleting expired URLs");
        urlService.deleteExpiredUrls();
    }
}