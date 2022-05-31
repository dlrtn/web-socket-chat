package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatRoom {

    private final String chatId;

    private final String chatName;

    private final String chatHostUser;

    private final ChatRoomType chatType;

    private final String chatPassword;

}
