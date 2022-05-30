package com.dlrtn.websocket.chat.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS("Success"),
    SERVER_ERROR("Failed by server error"),
    EXISTED_USER_ID("Failed by existed user id"),
    EXISTED_ROOM_NAME("Failed by existed room name")
    ;

    private final String description;
}
