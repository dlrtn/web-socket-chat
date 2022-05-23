package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
