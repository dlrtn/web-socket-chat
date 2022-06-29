package com.dlrtn.websocket.chat.business.friend.exception;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.exception.CommonException;

public class FriendNotExistsException extends CommonException {

    private static final String message = "Friend not exists";

    private String userId;

    public FriendNotExistsException(User user) {
        super(String.format("%s, userId : %s", message, user.getUsername()));
    }

}
