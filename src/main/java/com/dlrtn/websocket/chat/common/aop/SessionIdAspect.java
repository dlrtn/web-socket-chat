package com.dlrtn.websocket.chat.common.aop;

import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

public class SessionIdAspect {

    @Pointcut("execution(public * com.dlrtn.websocket.chat.business.user.application..*(..))")
    private void publicTarget() {
    }

    @Before("publicTarget()")
    public void localDataTime(ProceedingJoinPoint pjp, LocalDateTime now) throws Throwable {
        UserSessionConstants userSessionConstants;

        pjp.proceed();
    }

}
