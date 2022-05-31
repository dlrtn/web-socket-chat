package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatRoomMember {

    private final long id;

    private final String chatId;

    private final String userId;

    private final ChatRoomMemberRole role;

    private final ChatRoomMemberStatus status;

    private final Long joinedAt;

    private final Long lastConnectedAt;

}
