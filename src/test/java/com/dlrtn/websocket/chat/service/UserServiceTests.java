package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.config.LightMyBatisTest;
import com.dlrtn.websocket.chat.model.UserAuthRole;
import com.dlrtn.websocket.chat.model.payload.CommonResponse;
import com.dlrtn.websocket.chat.model.payload.SignInRequest;
import com.dlrtn.websocket.chat.model.payload.SignUpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@LightMyBatisTest
public class UserServiceTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserService userService;

    @DisplayName("유저 회원가입 테스트")
    @Test
    void join_user_test() {

        SignUpRequest request = new SignUpRequest();

        request.setUserId("dlrtn");
        request.setPassword("1234");
        request.setRealName("wndlrtn");
        request.setAuthRole(UserAuthRole.USER);

        CommonResponse commonResponse = userService.signUp(request);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Success", commonResponse)
        );

    }

    @DisplayName("유저 로그인 테스트")
    @Test
    void login_user_test() {
        SignInRequest request = new SignInRequest();

        request.setUserId("dlrtn");
        request.setPassword("1234");

        CommonResponse commonResponse = userService.signIn(request);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Success", commonResponse)
        );
    }

    @DisplayName("유저 회원탈퇴 테스트")
    @Test
    void delete_user_test() {


    }
}
