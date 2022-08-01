package com.dlrtn.websocket.chat.business.user.model;

import com.dlrtn.websocket.chat.business.user.model.domain.UserAuthRole;
import com.dlrtn.websocket.chat.business.user.model.payload.*;

public class UserServiceTestsConstants {

    private static final String TEST_USERNAME = "1234";

    private static final String TEST_PASSWORD = "1234";

    private static final String TEST_REAL_NAME = "wndlrtn";

    private static final UserAuthRole TEST_AUTH_ROLE = UserAuthRole.USER;

    private static final String TEST_NEW_PASSWORD = "123";

    private static final String TEST_NEW_REAL_NAME = "wndlrtns";

    public static final SignUpRequest TEST_SIGN_UP_REQUEST =  SignUpRequest.builder()
            .username(TEST_USERNAME)
            .password(TEST_PASSWORD)
            .realName(TEST_REAL_NAME)
            .authRole(TEST_AUTH_ROLE)
            .build();

    public static final SignInRequest TEST_SIGN_IN_REQUEST  =  SignInRequest.builder()
            .username(TEST_USERNAME)
            .password(TEST_PASSWORD)
            .build();

    public static final ChangeUserRequest TEST_CHANGE_USER_PROFILE_REQUEST = ChangeUserRequest.builder()
            .existingPassword(TEST_PASSWORD)
            .newPassword(TEST_NEW_PASSWORD)
            .newRealName(TEST_NEW_REAL_NAME)
            .build();

    public static final WithdrawUserRequest TEST_WITHDRAW_USER_REQUEST = WithdrawUserRequest.builder()
            .password(TEST_PASSWORD)
            .build();
}
