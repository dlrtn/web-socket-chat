package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ChangeFriendStateRequest {

    private final boolean isBlocked;

    private final boolean isFavorite;

}
