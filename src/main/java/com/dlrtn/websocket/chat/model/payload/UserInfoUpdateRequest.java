package com.dlrtn.websocket.chat.model.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserInfoUpdateRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String realName;

}
