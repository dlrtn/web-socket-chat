package com.dlrtn.websocket.chat.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS("Success"),
    SERVER_ERROR("Failed by server error"),
    EXISTED_USER_ID("Failed by existed user id"),
    ;

    private final String description;
}
