package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WithdrawUserRequest {

    @NotBlank
    private String password;

}
