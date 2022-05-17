package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.model.ResponseMessage;
import com.dlrtn.websocket.chat.model.UserAuthRole;
import com.dlrtn.websocket.chat.model.UserSessionConstants;
import com.dlrtn.websocket.chat.model.UserSessionCreation;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.payload.*;

import com.dlrtn.websocket.chat.util.CookieUtils;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import java.util.UUID;

import static com.dlrtn.websocket.chat.model.PagePathConstants.*;
import static com.dlrtn.websocket.chat.model.RestApiConstants.*;

@SpringBootTest
public class UserServiceTests {

    String sessionId;

    @Autowired
    private UserService userService;

    @BeforeAll()
    public static void createHttpServlet() {

        MockHttpServletRequest request;
        MockHttpServletResponse response;

        request = new MockHttpServletRequest(POST, LOGIN);
        response = new MockHttpServletResponse();

        CookieUtils.setCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, String.valueOf(UUID.randomUUID()));
        request.setCookies(response.getCookies());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);

    }

    @DisplayName("유저 회원가입 기능 부분 테스트")
    @Test
    void join_user_test() {


        SignUpRequest requestBody = new SignUpRequest();

        requestBody.setUsername("123");
        requestBody.setPassword("123");
        requestBody.setRealName("wndlrtn");
        requestBody.setAuthRole(UserAuthRole.USER);

        CommonResponse commonResponse = userService.signUp(requestBody);

        Assertions.assertEquals(ResponseMessage.SUCCESS, commonResponse.getMessage());

    }

    @DisplayName("유저 회원가입 기능 부분 테스트")
    @Test
    void join_user_test_existed_user() {

        SignUpRequest requestBody = new SignUpRequest();

        requestBody.setUsername("66");
        requestBody.setPassword("77");
        requestBody.setRealName("wndlrtn");
        requestBody.setAuthRole(UserAuthRole.USER);

        CommonResponse commonResponse = userService.signUp(requestBody);

        Assertions.assertEquals(ResponseMessage.EXISTED_USER_ID, commonResponse.getMessage());

    }

    @DisplayName("유저 찾기 기능 부분 테스트")
    @Test
    void find_user_test() {

        String username = "11";

        User foundUser = userService.findOne(username);

    }

    @DisplayName("유저 로그인 기능 부분 테스트")
    @Test
    void login_user_test() {

        SignInRequest requestBody = new SignInRequest();

        requestBody.setUsername("66");
        requestBody.setPassword("77");

        UserSessionCreation sessionCreation = userService.signIn(sessionId, requestBody);

        Assertions.assertEquals(true, sessionCreation.isSuccess());

    }

    @DisplayName("유저 회원정보 변경 기능 부분 테스트")
    @Test
    void update_user_test() {

        SignInRequest signInRequest = new SignInRequest();

        signInRequest.setUsername("11");
        signInRequest.setPassword("22");

        userService.signIn(sessionId, signInRequest);

        UserInfoUpdateRequest requestBody = new UserInfoUpdateRequest();

        requestBody.setUsername("11");
        requestBody.setExistingPassword("22");
        requestBody.setNewRealName("1234");
        requestBody.setNewPassword("123");

        CommonResponse commonResponse = userService.update(sessionId, requestBody);

        User foundUser = userService.findOne(requestBody.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertEquals(true, commonResponse.isSuccess()),
                () -> Assertions.assertEquals(requestBody.getUsername(), foundUser.getUsername()),
                () -> Assertions.assertEquals(requestBody.getNewPassword(), foundUser.getPassword()),
                () -> Assertions.assertEquals(requestBody.getNewRealName(), foundUser.getRealName()));

    }

    @DisplayName("유저 회원탈퇴 기능 부분 테스트")
    @Test
    void withdrawal_user_test() {

        SignInRequest signInRequest = new SignInRequest();

        signInRequest.setUsername("11");
        signInRequest.setPassword("22");

        userService.signIn(sessionId, signInRequest);

        DeleteUserRequest requestBody = new DeleteUserRequest();

        requestBody.setUsername("66");
        requestBody.setPassword("77");

        User foundUser = userService.findOne(requestBody.getUsername());

        CommonResponse commonResponse = userService.deleteUser(sessionId, requestBody);

        Assertions.assertEquals(ResponseMessage.SUCCESS, commonResponse.getDescription());

    }

}
