package com.ecommerce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TrackTimeAspect {
    private static final Logger log = LoggerFactory.getLogger(TrackTimeAspect.class);
    private static final String LOG_MESSAGE_FORMAT = "API_NAME = {}, TIME_TAKEN = {}";

    @Around("execution(* com.ecommerce.controller..*(..)) || @annotation(com.ecommerce.aspect.TrackTime)")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        log.info(LOG_MESSAGE_FORMAT, joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        return retVal;
    }
}
