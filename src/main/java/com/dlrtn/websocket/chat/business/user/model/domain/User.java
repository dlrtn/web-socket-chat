package com.dlrtn.websocket.chat.business.user.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@ToString
public class User {

    private final long userNo;

    private final String username;

    private final String password;

    private final String realName;

    private final UserAuthRole authRole;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

}
