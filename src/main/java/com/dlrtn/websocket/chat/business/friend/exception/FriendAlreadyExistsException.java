package com.dlrtn.websocket.chat.business.friend.exception;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.exception.CommonException;

public class FriendAlreadyExistsException extends CommonException {

    private static final String message = "Friend Already Exists";

    private String userId;

    public FriendAlreadyExistsException(User user) {
        super(String.format("%s, userId : %s", message, user.getUsername()));
    }

}
