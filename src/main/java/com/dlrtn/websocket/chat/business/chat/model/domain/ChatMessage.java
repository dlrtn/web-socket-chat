package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Data;

@Data
public class ChatMessage {

    private String roomId;

    private String sender;

    private String message;

}
