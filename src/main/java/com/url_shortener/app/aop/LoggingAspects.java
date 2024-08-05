package com.url_shortener.app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspects {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspects.class);

    @Before("execution(* com.url_shortener.app.service.impl.UrlServiceImpl.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
    LOGGER.info("** METHOD CALLED: " + joinPoint.getSignature().getName()  );
    }

    @After("execution(* com.url_shortener.app.service.impl.UrlServiceImpl.*(..))")
    public void logMethodExecuted(JoinPoint joinPoint) {
    LOGGER.info("** METHOD EXCEUTED: " + joinPoint.getSignature().getName() );
    }

    @AfterThrowing("execution(* com.url_shortener.app.service.impl.UrlServiceImpl.*(..))")
    public void logMethodCrashed(JoinPoint joinPoint) {
    LOGGER.info("** METHOD HAS SOME ISSUES: " + joinPoint.getSignature().getName()+ "**");
    }


    @AfterReturning("execution(* com.url_shortener.app.service.impl.UrlServiceImpl.*(..))")
    public void logMethodExecutedSuccess(JoinPoint joinPoint) {
    LOGGER.info("** METHOD EXECUTED SUCCESSFULLY: " + joinPoint.getSignature().getName());
    }


}
