package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoom {

    private final String roomId;

    private final String name;

    private final ChatRoomType chatRoomType;

    private final String roomPassword;

}
