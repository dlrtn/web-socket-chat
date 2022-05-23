package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteUserRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
