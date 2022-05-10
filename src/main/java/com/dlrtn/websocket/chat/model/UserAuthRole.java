package com.dlrtn.websocket.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserAuthRole {
    USER("유저 권한"),
    ADMIN("어드민 권한"),
    ;

    private final String description;

}
