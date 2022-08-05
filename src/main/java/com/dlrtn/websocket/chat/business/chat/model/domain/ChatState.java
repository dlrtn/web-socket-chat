package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatState {

    private final String chatName;

}
