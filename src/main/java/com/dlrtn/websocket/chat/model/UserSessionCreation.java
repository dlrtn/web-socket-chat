package com.dlrtn.websocket.chat.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class UserSessionCreation {

    private final boolean success;

    private final String sessionId;

    private final String failReason;

    public static UserSessionCreation successWith(String sessionId) {
        return of(true, sessionId, null);
    }

    public static UserSessionCreation failWith(String failReason) {
        return of(false, null, failReason);
    }

}
