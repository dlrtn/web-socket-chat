package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Data;
import lombok.Builder;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ChatRoom {

    private final String roomId;

    private final String name;

    private final ChatRoomType chatRoomType;

    private final String roomPassword;

}
