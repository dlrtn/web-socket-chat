package com.dlrtn.websocket.chat.business.user.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class FriendNotExistsException extends CommonException {

    private static final String message = "Friend not exists";

    private String userId;

    public FriendNotExistsException(String userId) {
        super(String.format("%s, userId : %s", message, userId));
    }

}
