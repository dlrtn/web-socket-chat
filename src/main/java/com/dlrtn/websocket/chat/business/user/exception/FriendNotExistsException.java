package com.dlrtn.websocket.chat.business.user.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class FriendNotExistsException extends CommonException {

    private static final String message = "Friend not exists";

    public FriendNotExistsException() {
        super(message);
    }

}
