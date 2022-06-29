package com.dlrtn.websocket.chat.business.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class ChatJoinMessage {

    private String chatId;

    private List<String> invitedIds;

}
