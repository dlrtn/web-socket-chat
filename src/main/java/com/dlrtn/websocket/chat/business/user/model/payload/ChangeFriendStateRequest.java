package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeFriendStateRequest {

    private final boolean isBlocked;

    private final boolean isFavorite;

}
