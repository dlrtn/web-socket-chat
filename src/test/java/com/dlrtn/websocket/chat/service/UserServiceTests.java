package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.model.ResponseMessage;
import com.dlrtn.websocket.chat.model.UserAuthRole;
import com.dlrtn.websocket.chat.model.payload.CommonResponse;
import com.dlrtn.websocket.chat.model.payload.SignInRequest;
import com.dlrtn.websocket.chat.model.payload.SignUpRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
public class UserServiceTests {

    protected MockHttpSession session;
    protected MockHttpServletRequest mockHttpRequest;

    @Autowired
    private UserService userService;

    @DisplayName("유저 회원가입 기능 부분 테스트")
    @Test
    void join_user_test() {

        SignUpRequest request = new SignUpRequest();

        request.setUserId("11");
        request.setPassword("22");
        request.setRealName("wndlrtn");
        request.setAuthRole(UserAuthRole.USER);

        CommonResponse commonResponse = userService.signUp(request);

        Assertions.assertEquals(ResponseMessage.SUCCESS, commonResponse.getMessage());

    }

    @DisplayName("유저 로그인 기능 부분 테스트")
    @Test
    void login_user_test() {

        SignInRequest request = new SignInRequest();

        request.setUserId("11");
        request.setPassword("22");

        session = new MockHttpSession();
        session.setAttribute("request", request);

        mockHttpRequest = new MockHttpServletRequest();
        mockHttpRequest.setSession(session);

        

    }

}
