package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.model.ResponseMessage;
import com.dlrtn.websocket.chat.model.UserAuthRole;
import com.dlrtn.websocket.chat.model.UserSessionConstants;
import com.dlrtn.websocket.chat.model.UserSessionCreation;
import com.dlrtn.websocket.chat.model.payload.CommonResponse;
import com.dlrtn.websocket.chat.model.payload.SignInRequest;
import com.dlrtn.websocket.chat.model.payload.SignUpRequest;

import com.dlrtn.websocket.chat.util.CookieUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import java.util.UUID;

@SpringBootTest
public class UserServiceTests {

    private MockHttpSession session;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockCookie cookie;
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

        SignInRequest requestBody = new SignInRequest();

        requestBody.setUserId("11");
        requestBody.setPassword("22");

        cookie = new MockCookie("SSID", "Random");
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest("POST", "/sign-in");

        CookieUtils.setCookie(response, "SSID", String.valueOf(UUID.randomUUID()));

        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        UserSessionCreation sessionCreation = userService.signIn(sessionId, requestBody);

        Assertions.assertEquals(true, sessionCreation.isSuccess());

    }

}
