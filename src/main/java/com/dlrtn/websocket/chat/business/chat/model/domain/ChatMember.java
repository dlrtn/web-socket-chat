package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatMember {

    private final long id;

    private final String chatId;

    private final String userId;

    private final ChatMemberRole role;

    private final ChatMemberStatus status;

    private final Long lastConnectedAt;

}
