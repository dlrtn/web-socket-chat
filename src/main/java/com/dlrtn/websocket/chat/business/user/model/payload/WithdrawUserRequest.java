package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
public class WithdrawUserRequest {

    @NotBlank
    private String password;

}
