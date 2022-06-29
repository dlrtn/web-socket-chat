package com.dlrtn.websocket.chat.business.friend.model;

import com.dlrtn.websocket.chat.business.user.model.domain.UserAuthRole;
import com.dlrtn.websocket.chat.business.user.model.payload.SignUpRequest;

public class FriendServiceTestsConstants {

    private static final String TEST_FRIEND_USERNAME = "1";

    private static final String TEST_FRIEND_PASSWORD = "1";

    private static final String TEST_FRIEND_REAL_NAME = "name";

    private static final UserAuthRole TEST_AUTH_ROLE = UserAuthRole.USER;

    public static final String TEST_FRIEND_NAME_USER_DECIDED = "stupid";

    public static final SignUpRequest TEST_FRIEND_SIGN_UP_REQUEST = SignUpRequest.builder()
            .username(TEST_FRIEND_USERNAME)
            .password(TEST_FRIEND_PASSWORD)
            .realName(TEST_FRIEND_REAL_NAME)
            .authRole(TEST_AUTH_ROLE)
            .build();

}
