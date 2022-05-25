package com.dlrtn.websocket.chat.common.aop;

import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.payload.SignInResponse;
import com.dlrtn.websocket.chat.business.user.repository.InMemorySessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CheckLoginStatusAop {

    private final UserService userService;

    private final InMemorySessionRepository sessionRepository;

    if (sessionRepository.exists(sessionId)) {
        return SignInResponse.successWith(sessionId);
    }

    @Before(value = "@annotation(CheckLoginStatus)")
    public void checkStatus (CheckLoginStatus ) {


    }

}
