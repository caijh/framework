package com.github.caijh.framework.core.aspect;

import com.github.caijh.framework.core.annotations.Retry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.github.caijh.framework.core.annotations.Retry)")
    public void pointcut() {
        // do nothing
    }

    @AfterThrowing(pointcut = "pointcut()")
    public void retry(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Retry retry = methodSignature.getMethod().getAnnotation(Retry.class);
        int i = 1;
        MethodInvocationProceedingJoinPoint methodPoint = ((MethodInvocationProceedingJoinPoint) joinPoint);
        while (i <= retry.times()) {
            try {
                Thread.sleep(retry.sleep());
                methodPoint.proceed();
            } catch (InterruptedException e) {
                logger.error("thread is interrupted", e);
                Thread.currentThread().interrupt();
            } catch (Throwable cause) {
                logger.error("method process fail, retry time = {}", i, cause);
            }
            i++;
        }
    }

}
