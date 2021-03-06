package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ChangeUserProfileRequest {

    private String newRealName; // 이름 변경 안할 경우

    private String newPassword; // 패스워드 변경 안할 경우

    @NotBlank
    private String existingPassword;

}
