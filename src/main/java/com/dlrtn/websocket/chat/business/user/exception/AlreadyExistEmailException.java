package com.dlrtn.websocket.chat.business.user.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class AlreadyExistEmailException extends CommonException {

    private static final String message = "Failed by existed user id";

    public AlreadyExistEmailException() {
        super(message);
    }

}
