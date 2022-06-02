package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
@Setter
@ToString
public class ChatMessage {

    private String chatId;

    private String sender;

    private String message;

}
