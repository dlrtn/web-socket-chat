package com.dlrtn.websocket.chat.business.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatRoomState {

    private final String chatName;

}
