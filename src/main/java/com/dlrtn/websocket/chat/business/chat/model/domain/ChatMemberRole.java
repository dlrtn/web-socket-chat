package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatMemberRole {

    HOST("채팅방 생성자, 채팅, 방관리 일괄 가능"),
    ADMIN("채팅방 관리자, 채팅, 방관리 일부(채팅방 삭제 제외) 가능"),
    GUEST("채팅방 참가자, 채팅만 가능"),
    ;

    private final String description;

}
