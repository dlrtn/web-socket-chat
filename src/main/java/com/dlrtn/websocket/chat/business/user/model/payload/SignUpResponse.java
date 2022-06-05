package com.dlrtn.websocket.chat.business.user.model.payload;

import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class SignUpResponse {

    private final boolean success;

    private final ResponseMessage message;

    private final String description;

    public static SignUpResponse success() {
        return successWith(ResponseMessage.SUCCESS);
    }

    public static SignUpResponse successWith(ResponseMessage message) {
        return of(true, message, message.getDescription());
    }

}
