package com.dlrtn.websocket.chat.business.friend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
public class FriendInformation {

    private final String friendName;

    private final boolean isBlocked;

    private final boolean isFavorite;

}
