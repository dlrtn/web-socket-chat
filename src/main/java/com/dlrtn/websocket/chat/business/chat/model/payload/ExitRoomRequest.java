package com.dlrtn.websocket.chat.business.chat.model.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExitRoomRequest {

    @NotBlank
    private String roomId;

}
