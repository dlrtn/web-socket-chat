package com.dlrtn.websocket.chat.business.user.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class FailedToSignOutException extends CommonException {

    private static final String message = "User sessionId is not exists in sessionRepository";

    public FailedToSignOutException() {
        super(message);
    }

}
