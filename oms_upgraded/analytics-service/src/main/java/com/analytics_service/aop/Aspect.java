package com.analytics_service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.analytics_service.controller..*(..)) || execution(* com.analytics_service.service..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long timeTaken = System.currentTimeMillis() - start;

            log.info("Method={} TimeTaken={}ms",
                    joinPoint.getSignature().toShortString(),
                    timeTaken);
        }
    }
}