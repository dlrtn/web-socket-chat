package com.dlrtn.websocket.chat.business.friend.exception;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.exception.CommonException;

public class FriendNotFoundException extends CommonException {

    private static final String message = "Friend can't found";

    private String userId;

    public FriendNotFoundException(User user) {
        super(String.format("%s, userId : %s", message, user.getUsername()));
    }

}
