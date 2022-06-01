package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.UserServiceTestsConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Predicate;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @BeforeEach
    void initialize_session_user(TestInfo info) {
        boolean needUserRecord = Optional.ofNullable(info)
                .map(TestInfo::getDisplayName)
                .filter(Predicate.not("유저 회원가입 기능 테스트"::equals))
                .isPresent();

        if (needUserRecord) {
            userService.signUp(UserServiceTestsConstants.TEST_SIGN_UP_REQUEST);
        }
    }

    @DisplayName("유저 회원가입 기능 테스트")
    @Test
    void join_user_test() {
        SignUpRequest requestBody = UserServiceTestsConstants.TEST_SIGN_UP_REQUEST;

        SignUpResponse firstSignUpResponse = userService.signUp(requestBody);
        SignUpResponse secondSignUpResponse = userService.signUp(requestBody);

        Assertions.assertEquals(ResponseMessage.SUCCESS, firstSignUpResponse.getMessage());
        Assertions.assertEquals(ResponseMessage.EXISTED_USER_ID, secondSignUpResponse.getMessage());
    }

    @DisplayName("유저 로그인 기능 부분 테스트")
    @Test
    void login_user_test() {
        SignInRequest requestBody = UserServiceTestsConstants.TEST_SIGN_IN_REQUEST;

        SignInResponse signInResponse = userService.signIn(null, requestBody);

        Assertions.assertTrue(signInResponse.isSuccess());
    }

    @DisplayName("유저 회원정보 변경 기능 부분 테스트")
    @Test
    void update_user_test() {
        String sessionId = getSessionId();
        ChangeUserProfileRequest request = UserServiceTestsConstants.TEST_CHANGE_USER_PROFILE_REQUEST;

        ChangeUserProfileResponse response = userService.changeUserProfile(sessionId, request);
        User sessionUser = userService.getSessionUser(sessionId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertFalse(userService.hasNotMatchedPassword(sessionUser, request.getNewPassword())),
                () -> Assertions.assertEquals(request.getNewRealName(), sessionUser.getRealName()));
    }

    @DisplayName("유저 회원탈퇴 기능 부분 테스트")
    @Test
    void withdraw_user_test() {
        String sessionId = getSessionId();
        WithdrawUserRequest request = UserServiceTestsConstants.TEST_WITHDRAW_USER_REQUEST;

        WithdrawUserResponse response = userService.withdrawUser(sessionId, request);

        Assertions.assertTrue(response.isSuccess());
    }

    private String getSessionId() {
        return Optional.of(UserServiceTestsConstants.TEST_SIGN_IN_REQUEST)
                .map(request -> userService.signIn(null, request))
                .map(SignInResponse::getSessionId)
                .orElseThrow(() -> new CommonException("Failed to get sign in session id"));
    }

}
