package com.url_shortener.app.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.url_shortener.app.controller.*.*(..))", throwing = "ex")
    public void handleControllerException(Throwable ex) {
        LOGGER.error("Exception occurred: " + ex.getMessage(), ex);
    }

}
