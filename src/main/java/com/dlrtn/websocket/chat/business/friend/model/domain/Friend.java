package com.dlrtn.websocket.chat.business.friend.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@ToString
public class Friend {

    private final long id;

    private final String userId;

    private final String friendId;

    private final String friendName;

    private final boolean isBlocked;

    private final boolean isFavorite;

    private final LocalDateTime createdAt;

}
