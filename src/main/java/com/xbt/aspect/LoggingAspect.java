package com.xbt.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    /**
     *
     * 打印方法参数
     */

    @Around("execution(* com.xbt.controller..*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Object[] args = joinPoint.getArgs();

        log.info("Method [{}] called with parameter types: {} and arguments: {}", methodName, Arrays.toString(parameterTypes), Arrays.toString(args));

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in method [{}]: {}", methodName, throwable.getMessage());
            throw throwable;
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("Method [{}] took [{}] ms returned [{}]  ", methodName,duration ,result);

        return result;
    }
}