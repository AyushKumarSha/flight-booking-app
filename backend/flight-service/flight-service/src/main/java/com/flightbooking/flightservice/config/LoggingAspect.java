package com.flightbooking.flightservice.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.flightbooking.flightservice.controller..*(..)) || " +
              "execution(* com.flightbooking.flightservice.service..*(..))")
    public void applicationMethods() {}

    @Before("applicationMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        log.info("→ ENTERING {}.{}() with args: {}",
                className, methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "applicationMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("← EXITING {}.{}() with result: {}",
                className, methodName, result);
    }

    @AfterThrowing(pointcut = "applicationMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.error("✗ EXCEPTION in {}.{}() — {}: {}",
                className, methodName,
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }

    @Around("applicationMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("⏱ {}.{}() executed in {}ms", className, methodName, duration);
        return result;
    }
}