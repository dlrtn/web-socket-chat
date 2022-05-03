package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.ResponseMessage;
import com.dlrtn.websocket.chat.model.UserAuthRole;
import com.dlrtn.websocket.chat.model.UserSessionConstants;
import com.dlrtn.websocket.chat.model.UserSessionCreation;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.payload.*;

import com.dlrtn.websocket.chat.repository.InMemorySessionRepository;
import com.dlrtn.websocket.chat.util.CookieUtils;
import com.fasterxml.jackson.databind.jsontype.impl.AsExistingPropertyTypeSerializer;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import java.time.temporal.ChronoUnit;
import java.util.UUID;

@SpringBootTest
public class UserServiceTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private InMemorySessionRepository sessionRepository = new InMemorySessionRepository();
    String sessionId;

    @Autowired
    private UserService userService;

    @BeforeEach
    void createHttpServlet() {

        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest("POST", "/sign-in");

        CookieUtils.setCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, String.valueOf(UUID.randomUUID()));
        request.setCookies(response.getCookies());

        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);

    }

    @BeforeEach
    void login() {
        SignInRequest requestBody = new SignInRequest();

        requestBody.setUserId("66");
        requestBody.setPassword("77");

        UserSessionCreation sessionCreation = userService.signIn(sessionId, requestBody);

    }

    @DisplayName("유저 회원가입 기능 부분 테스트")
    @Test
    void join_user_test() {

        SignUpRequest request = new SignUpRequest();

        request.setUserId("66");
        request.setPassword("77");
        request.setRealName("wndlrtn");
        request.setAuthRole(UserAuthRole.USER);

        CommonResponse commonResponse = userService.signUp(request);

        Assertions.assertEquals(ResponseMessage.SUCCESS, commonResponse.getMessage());

    }

    @DisplayName("유저 회원가입 기능 부분 테스트")
    @Test
    void join_user_test_existed_user() {

        SignUpRequest request = new SignUpRequest();

        request.setUserId("66");
        request.setPassword("77");
        request.setRealName("wndlrtn");
        request.setAuthRole(UserAuthRole.USER);

        CommonResponse commonResponse = userService.signUp(request);

        Assertions.assertEquals(ResponseMessage.EXISTED_USER_ID, commonResponse.getMessage());

    }

    @DisplayName("유저 찾기 기능 부분 테스트")
    @Test
    void find_user_test() {

        String userId = "11";

        User foundUser = userService.findOne(userId);

    }

    @DisplayName("유저 로그인 기능 부분 테스트")
    @Test
    void login_user_test() {

        SignInRequest requestBody = new SignInRequest();

        requestBody.setUserId("66");
        requestBody.setPassword("77");

        UserSessionCreation sessionCreation = userService.signIn(sessionId, requestBody);

        Assertions.assertEquals(true, sessionCreation.isSuccess());

    }

    @DisplayName("유저 회원정보 변경 기능 부분 테스트")
    @Test
    void update_user_test() {

        UserInfoUpdateRequest requestBody = new UserInfoUpdateRequest();

        requestBody.setUserId("11");
        requestBody.setExistingPassword("22");
        requestBody.setNewRealName("1234");
        requestBody.setNewPassword("123");

        CommonResponse commonResponse = userService.update(sessionId, requestBody);

        User foundUser = userService.findOne(requestBody.getUserId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(requestBody.getUserId(), foundUser.getUserId()),
                () -> Assertions.assertEquals(requestBody.getNewPassword(), foundUser.getPassword()),
                () -> Assertions.assertEquals(requestBody.getNewRealName(), foundUser.getRealName()));

    }

    @DisplayName("유저 회원탈퇴 기능 부분 테스트")
    @Test
    void withdrawal_user_test() {

        DeleteUserRequest requestBody = new DeleteUserRequest();

        requestBody.setUserId("66");
        requestBody.setPassword("77");

        User foundUser = userService.findOne(requestBody.getUserId());

        CommonResponse commonResponse = userService.deleteUser(sessionId, requestBody);

        Assertions.assertEquals(ResponseMessage.SUCCESS, commonResponse.getDescription());

    }

}
