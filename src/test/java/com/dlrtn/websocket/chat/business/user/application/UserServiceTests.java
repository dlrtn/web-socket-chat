package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.domain.UserAuthRole;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.business.user.repository.InMemorySessionRepository;
import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import com.dlrtn.websocket.chat.util.CookieUtils;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
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
    private InMemorySessionRepository sessionRepository;

    @Autowired
    private UserService userService;

    public void createHttpServlet() {
        MockHttpServletRequest request;
        MockHttpServletResponse response;

        request = new MockHttpServletRequest(HttpMethod.POST.name(), LOGIN);
        response = new MockHttpServletResponse();

        CookieUtils.setCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, String.valueOf(UUID.randomUUID()));
        request.setCookies(response.getCookies());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
    }

//    @DisplayName("유저 회원가입 기능 테스트")
//    @Test
//    void join_user_test() {
//        SignUpRequest requestBody = new SignUpRequest();
//
//        requestBody.setUsername("123");
//        requestBody.setPassword("123");
//        requestBody.setRealName("wndlrtn");
//        requestBody.setAuthRole(UserAuthRole.USER);
//
//        SignUpResponse signUpResponse = userService.signUp(requestBody);
//
//        Assertions.assertEquals(ResponseMessage.SUCCESS, signUpResponse.getMessage());
//    }

//    @DisplayName("유저 회원가입 기능 부분 테스트")
//    @Test
//    void join_user_test_existed_user() {
//        SignUpRequest requestBody = new SignUpRequest();
//
//        requestBody.setUsername("123");
//        requestBody.setPassword("123");
//        requestBody.setRealName("wndlrtn");
//        requestBody.setAuthRole(UserAuthRole.USER);
//
//        SignUpResponse signUpResponse = userService.signUp(requestBody);
//
//        Assertions.assertEquals(ResponseMessage.EXISTED_USER_ID, signUpResponse.getMessage());
//    }

    @DisplayName("유저 로그인 기능 부분 테스트")
    @Test
    void login_user_test() {
        SignInRequest requestBody = new SignInRequest();

        requestBody.setUsername("123");
        requestBody.setPassword("123");

        SignInResponse signInResponse = userService.signIn(sessionId, requestBody);

        Assertions.assertTrue(signInResponse.isSuccess());
    }

    @DisplayName("유저 회원정보 변경 기능 부분 테스트")
    @Test
    void update_user_test() {
        createHttpServlet();

        SignInRequest signInRequest = new SignInRequest();

        signInRequest.setUsername("123");
        signInRequest.setPassword("123");

        SignInResponse signInResponse = userService.signIn(sessionId, signInRequest);

        ChangeUserProfileRequest request = new ChangeUserProfileRequest();

        request.setExistingPassword("123");
        request.setNewRealName("1234");
        request.setNewPassword("1243");

        User user = sessionRepository.get(signInResponse.getSessionId());

        ChangeUserProfileResponse response = userService.changeUserProfile(user, request);

        User foundUser = sessionRepository.get(sessionId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(request.getNewPassword(), foundUser.getPassword()),
                () -> Assertions.assertEquals(request.getNewRealName(), foundUser.getRealName()));
    }

    @DisplayName("유저 회원탈퇴 기능 부분 테스트")
    @Test
    void withdrawal_user_test() {
        createHttpServlet();

        SignInRequest signInRequest = new SignInRequest();

        signInRequest.setUsername("123");
        signInRequest.setPassword("123");

        SignInResponse signInResponse = userService.signIn(sessionId, signInRequest);

        WithdrawUserRequest request = new WithdrawUserRequest();

        request.setPassword("11");

        User foundUser = sessionRepository.get(signInResponse.getSessionId());

        WithdrawUserResponse response = userService.withdrawUser(foundUser, request);

        Assertions.assertTrue(response.isSuccess());
    }

}
