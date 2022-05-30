package com.dlrtn.websocket.chat.business.chat.model.payload;

import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class FindRoomResponse {

    private final boolean success;

    private final ResponseMessage message;

    private final String description;

    public static FindRoomResponse success() {
        return successWith(ResponseMessage.SUCCESS);
    }

    public static FindRoomResponse successWith(ResponseMessage message) {
        return of(true, message, message.getDescription());
    }

    public static FindRoomResponse failWith(ResponseMessage message) {
        return of(false, message, message.getDescription());
    }

    public static FindRoomResponse failWith(String description) {
        return of(false, ResponseMessage.SERVER_ERROR, description);
    }

}
