package com.ecommerce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;

import static com.ecommerce.util.Constant.CORRELATION_ID;

@Aspect
@Component
public class TrackTimeAspect {
    private static final Logger log = LoggerFactory.getLogger(TrackTimeAspect.class);
    private static final String LOG_MESSAGE_FORMAT = "API_NAME = {}, TIME_TAKEN = {}";

    @Around("execution(* com.ecommerce.controller..*(..)) || @annotation(com.ecommerce.aspect.Track)")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        log.info(LOG_MESSAGE_FORMAT, joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        MDC.remove(CORRELATION_ID);
        return retVal;
    }
}
