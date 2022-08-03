package com.dlrtn.websocket.chat.business.chat.model.payload;

import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class CreateChatResponse {

    private final boolean success;

    private final ResponseMessage message;

    private final String description;

    public static CreateChatResponse success() {
        return successWith(ResponseMessage.SUCCESS);
    }

    public static CreateChatResponse successWith(ResponseMessage message) {
        return of(true, message, message.getDescription());
    }

}

