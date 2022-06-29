package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;

@Getter
@Builder
@ToString
public class ChatMessage {

    private ChatMessageType type;

    private String chatId;

    private String sender;

    private String message;

}
