package com.dlrtn.websocket.chat.common.aop;

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
public class ServiceLoggingAspect {

    @Pointcut("execution(public * com.dlrtn.websocket.chat.business.user.service..*(..))") //service 로직 밑의 모든 로직들에 적용
    private void publicTarget() {
    }

    @Around("publicTarget()")
    public Object logServiceMethodPerformance(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();

        Object result = pjp.proceed();

        sw.stop();
        log.info("걸린시간: {} ms", sw.getLastTaskTimeMillis());

        return result;
    }

}
