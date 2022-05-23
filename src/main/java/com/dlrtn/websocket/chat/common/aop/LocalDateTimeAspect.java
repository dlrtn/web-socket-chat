package com.dlrtn.websocket.chat.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

@Slf4j
public class LocalDateTimeAspect {

    @Pointcut("execution(public * com.dlrtn.websocket.chat.business.user.application..*(..))")
    private void publicTarget() {
    }

    @Before("publicTarget() && args(now)")
    public void localDataTime(ProceedingJoinPoint pjp, LocalDateTime now) throws Throwable {
        log.info("현재 시간 호출"); //TODO AOP에서 매개변수 전달하여 파라미터로 사용하는 방법
        pjp.proceed();
    }

}
