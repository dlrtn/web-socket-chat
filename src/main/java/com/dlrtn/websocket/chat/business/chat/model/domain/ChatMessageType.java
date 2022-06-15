package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatMessageType {

    USER("사용자 메시지"),
    NOTICE("입장, 퇴장 등 채팅방 자체 제공 메시지"),
    ;

    private final String description;

}
