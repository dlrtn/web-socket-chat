package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.FriendServiceTestsConstants;
import com.dlrtn.websocket.chat.business.user.model.UserServiceTestsConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.Friend;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateRequest;
import com.dlrtn.websocket.chat.business.user.model.payload.SignInResponse;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.common.model.CommonResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Predicate;

@SpringBootTest
public class FriendServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @BeforeEach
    void initialize_session_user(TestInfo info) {
        boolean needUserRecord = Optional.ofNullable(info)
                .map(TestInfo::getDisplayName)
                .filter(Predicate.not("유저 회원가입 기능 테스트"::equals))
                .isPresent();

        if (needUserRecord) {
            userService.signUp(UserServiceTestsConstants.TEST_SIGN_UP_REQUEST);
            userService.signUp(FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST);
        }
    }

    private String getSessionId() {
        return Optional.of(UserServiceTestsConstants.TEST_SIGN_IN_REQUEST)
                .map(request -> userService.signIn(null, request))
                .map(SignInResponse::getSessionId)
                .orElseThrow(() -> new CommonException("Failed to get sign in session id"));
    }

    @DisplayName("친구 추가 기능 테스트")
    @Test
    void add_friend_test() {
        String sessionId = getSessionId();

        CommonResponse response = friendService.addFriend(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        User friendUser = friendService.getFriend(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(friendUser.getUsername(), FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername()),
                () -> Assertions.assertEquals(friendUser.getRealName(), FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getRealName()));
    }

    @DisplayName("친구 삭제 기능 테스트")
    @Test
    void delete_friend_test() {
        String sessionId = getSessionId();

        friendService.addFriend(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());
        CommonResponse response = friendService.deleteFriend(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        User friendUser = friendService.getFriend(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(friendUser, null));
    }

    @DisplayName("회원 차단목록 추가 기능 테스트")
    @Test
    void add_friend_block_list() {
        String sessionId = getSessionId();

        ChangeFriendStateRequest request = ChangeFriendStateRequest.builder()
                .isBlocked(true)
                .isFavorite(false)
                .build();

        CommonResponse response = friendService.changeFriendState(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername(), request);

        Friend friendRelation = friendService.getFriendRelation(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(friendRelation.getFriendId(), FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername()),
                () -> Assertions.assertTrue(friendRelation.isBlocked()),
                () -> Assertions.assertFalse(friendRelation.isFavorite()));
    }

    @DisplayName("회원 즐겨찾기 추가 기능 테스트")
    @Test
    void add_friend_favorite_list() {
        String sessionId = getSessionId();

        ChangeFriendStateRequest request = ChangeFriendStateRequest.builder()
                .isBlocked(false)
                .isFavorite(true)
                .build();

        CommonResponse response = friendService.changeFriendState(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername(), request);

        Friend friendRelation = friendService.getFriendRelation(sessionId, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(friendRelation.getFriendId(), FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername()),
                () -> Assertions.assertFalse(friendRelation.isBlocked()),
                () -> Assertions.assertTrue(friendRelation.isFavorite()));
    }

}
