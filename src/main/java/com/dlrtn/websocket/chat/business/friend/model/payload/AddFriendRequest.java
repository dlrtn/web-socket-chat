package com.dlrtn.websocket.chat.business.friend.model.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class AddFriendRequest {

    private final String friendName;

    private final String friendId;

    private LocalDateTime createdAt; //front에서 받도록? 내부에서 정의?

}
