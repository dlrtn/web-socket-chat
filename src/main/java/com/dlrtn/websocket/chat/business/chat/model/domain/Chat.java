package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Chat {

    private final String chatId;

    private final String chatName;

    private final String chatHostUser;

    private final ChatType chatType;

}
