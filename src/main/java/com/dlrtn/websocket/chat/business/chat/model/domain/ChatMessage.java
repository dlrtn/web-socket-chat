package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessage {

    private String chatId;

    private String sender;

    private String message;

}
