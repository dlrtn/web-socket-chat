package com.dlrtn.websocket.chat.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LogAop {

    @Pointcut("execution(public * com.dlrtn.websocket.chat.service..*(..))")
    private void publicTarget() {
    }

    @Around("publicTarget()")
    public Object calcPerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("성능 측정을 시작합니다.");
        StopWatch sw = new StopWatch();
        sw.start(); // 비즈니스 로직 (메인 로직)

        Object result = pjp.proceed();

        sw.stop();
        log.info("성능 측정이 끝났습니다.");
        log.info("걸린시간: {} ms", sw.getLastTaskTimeMillis());
        return result;
    }

}
