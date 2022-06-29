package com.dlrtn.websocket.chat.business.friend.application;

import com.dlrtn.websocket.chat.business.friend.model.FriendState;
import com.dlrtn.websocket.chat.business.friend.model.FriendServiceTestsConstants;
import com.dlrtn.websocket.chat.business.friend.model.payload.*;
import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.UserServiceTestsConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.SignInResponse;
import com.dlrtn.websocket.chat.business.user.repository.UserRepository;
import com.dlrtn.websocket.chat.business.user.repository.UserSessionRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

@SpringBootTest
public class FriendServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserRepository userRepository;

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
            userService.signUp(FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST);
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

    @DisplayName("친구 추가 기능 테스트")
    @Test
    void add_friend_test() {
        User sessionUser = getSessionUser();

        AddFriendRequest request = AddFriendRequest.builder()
                .friendId(FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername())
                .friendName(FriendServiceTestsConstants.TEST_FRIEND_NAME_USER_DECIDED)
                .createdAt(LocalDateTime.now())
                .build();
        AddFriendResponse response = friendService.addFriend(sessionUser, request);

        String foundFriendId = FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername();

        User foundUser = userRepository.findByUsername(foundFriendId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(foundUser.getUsername(), FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername()),
                () -> Assertions.assertEquals(foundUser.getRealName(), FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getRealName()));
    }

    @DisplayName("친구 삭제 기능 테스트")
    @Test
    void delete_friend_test() {
        User sessionUser = getSessionUser();

        AddFriendRequest request = AddFriendRequest.builder()
                .friendId(FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername())
                .friendName(FriendServiceTestsConstants.TEST_FRIEND_NAME_USER_DECIDED)
                .createdAt(LocalDateTime.now())
                .build();
        friendService.addFriend(sessionUser, request);

        DeleteFriendResponse response = friendService.deleteFriend(sessionUser, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        String foundFriendId = FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername();

        User foundUser = userRepository.findByUsername(foundFriendId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(foundUser, null));
    }

    @DisplayName("회원 차단목록 추가 기능 테스트")
    @Test
    void add_friend_block_list() {
        User sessionUser = getSessionUser();

        ChangeFriendStateRequest request = ChangeFriendStateRequest.builder()
                .isBlocked(true)
                .isFavorite(false)
                .build();

        ChangeFriendStateResponse response = friendService.changeFriendState(sessionUser, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername(), request);

        FriendState friendstate = friendService.getFriendShip(sessionUser, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertTrue(friendstate.isBlocked()),
                () -> Assertions.assertFalse(friendstate.isFavorite()));
    }

    @DisplayName("회원 즐겨찾기 추가 기능 테스트")
    @Test
    void add_friend_favorite_list() {
        User sessionUser = getSessionUser();

        ChangeFriendStateRequest request = ChangeFriendStateRequest.builder()
                .isBlocked(false)
                .isFavorite(true)
                .build();

        ChangeFriendStateResponse response = friendService.changeFriendState(sessionUser, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername(), request);

        FriendState friendstate = friendService.getFriendShip(sessionUser, FriendServiceTestsConstants.TEST_FRIEND_SIGN_UP_REQUEST.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertFalse(friendstate.isBlocked()),
                () -> Assertions.assertTrue(friendstate.isFavorite()));
    }

}
