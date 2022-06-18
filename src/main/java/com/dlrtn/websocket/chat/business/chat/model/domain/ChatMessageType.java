package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatMessageType {

    NOTIFICATION("채팅방 내 공지 시스템"),
    BASIC("기본 메시지"),
    ;

    private final String description;

}
