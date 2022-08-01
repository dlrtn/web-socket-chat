package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.exception.AlreadyExistsUseridException;
import com.dlrtn.websocket.chat.business.user.model.UserServiceTestsConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.business.user.repository.UserSessionRepository;
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

    @Autowired
    private UserSessionRepository userSessionRepository;

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

    private String getSessionId() {
        return Optional.of(UserServiceTestsConstants.TEST_SIGN_IN_REQUEST)
                .map(request -> userService.signIn(null, request))
                .map(SignInResponse::getSessionId)
                .orElseThrow(() -> new CommonException("Failed to get sign in session id"));
    }

    private User getSessionUser() {
        return Optional.of(getSessionId())
                .flatMap(userSessionRepository::findById)
                .orElseThrow(() -> new CommonException("Failed to get session User"))
                .getSessionUser();
    }

    @DisplayName("유저 회원가입 기능 테스트")
    @Test
    void join_user_test() {
        SignUpRequest requestBody = UserServiceTestsConstants.TEST_SIGN_UP_REQUEST;

        SignUpResponse signUpResponse = userService.signUp(requestBody);

        Assertions.assertEquals(ResponseMessage.SUCCESS, signUpResponse.getMessage());
        Assertions.assertThrows(AlreadyExistsUseridException.class, () -> userService.signUp(requestBody));
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
        User sessionUser = getSessionUser();
        ChangeUserRequest request = UserServiceTestsConstants.TEST_CHANGE_USER_PROFILE_REQUEST;

        ChangeUserResponse response = userService.changeUser(sessionId, sessionUser, request);
        User profileChangedUser = userSessionRepository.findById(sessionId).orElseThrow().getSessionUser();

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertFalse(userService.validatePassword(profileChangedUser, request.getNewPassword())),
                () -> Assertions.assertEquals(request.getNewRealName(), profileChangedUser.getRealName()));
    }

    @DisplayName("유저 회원탈퇴 기능 부분 테스트")
    @Test
    void withdraw_user_test() {
        User sessionUser = getSessionUser();
        WithdrawUserRequest request = UserServiceTestsConstants.TEST_WITHDRAW_USER_REQUEST;

        WithdrawUserResponse response = userService.withdrawUser(sessionUser, request);

        Assertions.assertTrue(response.isSuccess());
    }

}
