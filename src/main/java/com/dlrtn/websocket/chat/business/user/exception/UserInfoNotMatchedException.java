package com.dlrtn.websocket.chat.business.user.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class UserInfoNotMatchedException extends CommonException {

    private static final String message = "User id or password not correct";

    public UserInfoNotMatchedException() {
        super(message);
    }

}
