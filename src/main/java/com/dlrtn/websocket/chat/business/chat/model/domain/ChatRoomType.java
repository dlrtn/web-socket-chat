package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatRoomType {
    SELF("개인"),
    PUBLIC("공개"),
    PRIVATE("비밀"),
    ;

    private final String description;
}
