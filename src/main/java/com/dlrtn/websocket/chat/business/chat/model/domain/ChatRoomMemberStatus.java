package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatRoomMemberStatus {
    WAIT("접속하지 않은 상태"),
    ACTIVE("접속중인 상태"),
    EXIT("퇴장한 상태"),
    ;

    private final String description;
}
