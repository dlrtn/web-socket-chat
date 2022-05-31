package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
@ToString
public class ChatMessage {

    private String chatId;

    private String sender;

    private String message;

}
