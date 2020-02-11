package com.ecommerce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;

import static com.ecommerce.util.Constant.CORRELATION_ID;

@Aspect
@Component
@Order(0)
public class CorrelationIdAspect {
    @Around("execution(* com.ecommerce.controller..*(..))")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
        Object retVal = joinPoint.proceed();
        MDC.remove(CORRELATION_ID);
        return retVal;
    }
}
