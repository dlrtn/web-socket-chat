package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class SignOutResponse {

    private final boolean success;

    public static SignOutResponse success() {
        return of(true);
    }

}
