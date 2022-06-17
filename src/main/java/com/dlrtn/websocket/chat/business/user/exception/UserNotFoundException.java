package com.dlrtn.websocket.chat.business.user.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class UserNotFoundException extends CommonException {

    private static final String message = "User not founded";

    public UserNotFoundException() {
        super(message);
    }

}
