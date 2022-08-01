package com.dlrtn.websocket.chat.business.user.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class FailedToUserAuthenticationException extends CommonException {

    private static final String message = "User id or password not correct";

    public FailedToUserAuthenticationException() {
        super(message);
    }

}
