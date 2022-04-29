package com.dlrtn.websocket.chat.model.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserInfoUpdateRequest {

    @NotBlank
    private String userId;

    private String newRealName; // 이름 변경 안할 경우

    @NotBlank
    private String existingPassword;

    private String newPassword; // 패스워드 변경 안할 경우

}
