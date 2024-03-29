package com.dlrtn.websocket.chat.business.friend.model.payload;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeFriendStateRequest {

    private final String friendName;

    private final boolean isBlocked;

    private final boolean isFavorite;

}
