package com.dlrtn.websocket.chat.business.friend.model.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class AddFriendRequest {

    private final String userName;

    private final String friendId;

    private final String friendName;

}
