package com.dlrtn.websocket.chat.business.chat.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Getter
public class ChangeChatRoomRequest {

    @NotBlank
    private final String chatId;

    @NotBlank
    private final String chatName;

}
