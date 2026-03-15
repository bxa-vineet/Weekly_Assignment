package com.order_service.aop;


import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Around("execution(* com.example.orderservice.controller..*(..)) || execution(* com.example.orderservice.service..*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        try {
            return joinPoint.proceed();
        } finally {
            long timeTaken = System.currentTimeMillis() - start;
            String requestInfo = request != null
                    ? request.getMethod() + " " + request.getRequestURI()
                    : "NON_HTTP_CALL";

            log.info("Request={} Method={} TimeTaken={}ms",
                    requestInfo,
                    joinPoint.getSignature().toShortString(),
                    timeTaken);
        }
    }
}

