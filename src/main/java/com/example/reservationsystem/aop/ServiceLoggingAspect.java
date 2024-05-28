package com.example.reservationsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect {
    @Before(value = "within(com.example.reservationsystem.service.*)")
    public void logBefore(JoinPoint joinpoint) {
        log.info("Entering method: " + joinpoint.getSignature().getName());
        log.info("Arguments: " + Arrays.toString(joinpoint.getArgs()));
    }

    @After(value = "execution(public * com.example.reservationsystem.service..*(..))")
    public void logAfter(JoinPoint joinpoint) {
        log.info("Exiting method: " + joinpoint.getSignature().getName());
    }

    @AfterReturning(value = "within(com.example.reservationsystem.service.*)", returning = "result")
    public void logAfterReturning(JoinPoint joinpoint, Object result) {
        log.info("Method executed: " + joinpoint.getSignature().getName());
        log.info("Result: " + result);
    }

    @AfterThrowing(value = "within(com.example.reservationsystem.service.*)", throwing = "error")
    public void logAfterThrowing(JoinPoint joinpoint, Throwable error) {
        log.error("Exception in method: " + joinpoint.getSignature().getName());
        log.error("Exception: ", error);
    }

}