package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class SignInResponse {

    private final boolean success;

    private final String sessionId;

    public static SignInResponse successWith(String sessionId) {
        return of(true, sessionId);
    }

}
