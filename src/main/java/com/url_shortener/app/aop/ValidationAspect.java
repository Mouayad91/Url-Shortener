package com.url_shortener.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    @Around("execution(* com.url_shortener.app.service.impl.UrlServiceImpl.getUrlById(..)) && args(urlId)")
    public Object validateInput(ProceedingJoinPoint jp, Long urlId) throws Throwable {

        if (urlId <= 0) {
            LOGGER.info("** Url ID cannot be negative, updating it to positive value");
            urlId = -urlId;
            LOGGER.info("New Url ID: " + urlId);
        }

        Object obj = jp.proceed(new Object[]{urlId});
        return obj;
    }
}
