package com.dlrtn.websocket.chat.business.chat.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@Getter
public class ExitRoomRequest {

    @NotBlank
    private final String roomId;

}
