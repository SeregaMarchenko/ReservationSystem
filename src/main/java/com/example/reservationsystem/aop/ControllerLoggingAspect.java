package com.example.reservationsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {
    @Before("execution(* com.example.reservationsystem.controller..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("HTTP request: " + joinPoint.getSignature().getName());
        log.info("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* com.example.reservationsystem.controller..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("HTTP response: " + joinPoint.getSignature().getName());
    }
}
