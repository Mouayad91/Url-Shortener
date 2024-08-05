package com.url_shortener.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceAspect {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceAspect.class);


    @Around("execution(* com.url_shortener.app.service.impl.UrlServiceImpl.*(..))")
    public Object monitorTime(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
       Object obj =  jp.proceed();
        long endTime = System.currentTimeMillis();
       
       
        // Logs the execution time
        long totalTime = endTime - startTime;
        LOGGER.info("** Time taken to execute the method "+ jp.getSignature().getName() + " is: " + totalTime + " milliseconds");
       
        return obj;
    }



}
