package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChatRoomMember {

    private Long id;

    private Long chatId;

    private Long userId;

    private ChatRoomMemberRole role;

    private ChatRoomMemberStatus status;

    private Long joinedAt;

    private Long lastConnectedAt;

}
