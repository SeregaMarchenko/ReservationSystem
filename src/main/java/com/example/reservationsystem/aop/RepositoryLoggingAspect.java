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
public class RepositoryLoggingAspect {
    @Before("execution(* com.example.reservationsystem.repository..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering repository method: " + joinPoint.getSignature().getName());
        log.info("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* com.example.reservationsystem.repository..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Exiting repository method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.example.reservationsystem.repository..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Repository method executed: " + joinPoint.getSignature().getName());
        log.info("Result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.reservationsystem.repository..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("Exception in repository method: " + joinPoint.getSignature().getName());
        log.error("Exception: ", error);
    }
}
