package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.domain.UserAuthRole;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import com.dlrtn.websocket.chat.util.CookieUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.UUID;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.LOGIN;

@SpringBootTest
public class UserServiceTests {

    String sessionId;

    @Autowired
    private UserService userService;

    @BeforeAll()
    public static void createHttpServlet() {
        MockHttpServletRequest request;
        MockHttpServletResponse response;

        request = new MockHttpServletRequest(HttpMethod.POST.name(), LOGIN);
        response = new MockHttpServletResponse();

        CookieUtils.setCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, String.valueOf(UUID.randomUUID()));
        request.setCookies(response.getCookies());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
    }

    @DisplayName("유저 회원가입 기능 부분 테스트")
    @Test
    void join_user_test() {
        /*SignUpRequest requestBody = new SignUpRequest();

        requestBody.setUsername("123");
        requestBody.setPassword("123");
        requestBody.setRealName("wndlrtn");
        requestBody.setAuthRole(UserAuthRole.USER);

        SingUpResponse signUpResponse = userService.signUp(requestBody);

        Assertions.assertEquals(ResponseMessage.SUCCESS, signUpResponse.getMessage());*/
    }

    @DisplayName("유저 회원가입 기능 부분 테스트")
    @Test
    void join_user_test_existed_user() {
        /*SignUpRequest requestBody = new SignUpRequest();

        requestBody.setUsername("66");
        requestBody.setPassword("77");
        requestBody.setRealName("wndlrtn");
        requestBody.setAuthRole(UserAuthRole.USER);

        CommonResponse commonResponse = userService.signUp(requestBody);

        Assertions.assertEquals(ResponseMessage.EXISTED_USER_ID, commonResponse.getMessage());*/
    }

    @DisplayName("유저 찾기 기능 부분 테스트")
    @Test
    void find_user_test() {
        String username = "11";

        User foundUser = userService.find(username);
    }

    @DisplayName("유저 로그인 기능 부분 테스트")
    @Test
    void login_user_test() {
        /*SignInResponse requestBody = new SignInResponse();*/

        /*requestBody.setUsername("66");
        requestBody.setPassword("77");

        UserSessionCreation sessionCreation = userService.signIn(sessionId, requestBody);

        Assertions.assertTrue(sessionCreation.isSuccess());*/
    }

    @DisplayName("유저 회원정보 변경 기능 부분 테스트")
    @Test
    void update_user_test() {
        /*SignInResponse signInResponse = new SignInResponse();
*/
        /*signInResponse.setUsername("11");
        signInResponse.setPassword("22");*/

        /*userService.signIn(sessionId, signInResponse);

        UpdateRequest requestBody = new UpdateRequest();

        requestBody.setUsername("11");
        requestBody.setExistingPassword("22");
        requestBody.setNewRealName("1234");
        requestBody.setNewPassword("123");

        CommonResponse commonResponse = userService.update(sessionId, requestBody);
*/
        /*User foundUser = userService.find(requestBody.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertTrue(commonResponse.isSuccess()),
                () -> Assertions.assertEquals(requestBody.getUsername(), foundUser.getUsername()),
                () -> Assertions.assertEquals(requestBody.getNewPassword(), foundUser.getPassword()),
                () -> Assertions.assertEquals(requestBody.getNewRealName(), foundUser.getRealName()));*/
    }

    @DisplayName("유저 회원탈퇴 기능 부분 테스트")
    @Test
    void withdrawal_user_test() {
        /*SignInResponse signInResponse = new SignInResponse();

        signInResponse.setUsername("11");
        signInResponse.setPassword("22");

        userService.signIn(sessionId, signInResponse);

        DeleteRequest requestBody = new DeleteRequest();

        requestBody.setUsername("66");
        requestBody.setPassword("77");

        User foundUser = userService.find(requestBody.getUsername());

        CommonResponse commonResponse = userService.delete(sessionId, requestBody);

        Assertions.assertEquals(ResponseMessage.SUCCESS.name(), commonResponse.getDescription());*/
    }

}
