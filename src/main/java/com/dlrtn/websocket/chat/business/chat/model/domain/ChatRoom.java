package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Data;
import lombok.Builder;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ChatRoom {

    private String roomId;

    private String name;

    private final Set<WebSocketSession> sessions = new HashSet<>();

}
