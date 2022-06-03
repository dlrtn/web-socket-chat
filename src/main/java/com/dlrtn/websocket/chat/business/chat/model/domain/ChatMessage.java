package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatMessage {

    private String chatId;

    private String sender;

    private String message;

}
