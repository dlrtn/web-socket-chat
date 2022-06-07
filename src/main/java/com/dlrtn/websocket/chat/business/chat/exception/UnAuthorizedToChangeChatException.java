package com.dlrtn.websocket.chat.business.chat.exception;

import com.dlrtn.websocket.chat.common.exception.CommonException;

public class UnAuthorizedToChangeChatException extends CommonException {

    private static final String message = "Failed by existed user id";

    public UnAuthorizedToChangeChatException(String userId, String chatId) {
        super(String.format("%s, userId : %s, friendId : %s", message, userId, chatId));
    }

}
