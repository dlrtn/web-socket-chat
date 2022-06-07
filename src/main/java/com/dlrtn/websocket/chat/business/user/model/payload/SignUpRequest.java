package com.dlrtn.websocket.chat.business.user.model.payload;

import com.dlrtn.websocket.chat.business.user.model.domain.UserAuthRole;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class SignUpRequest {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    private final String realName;

    @NotNull
    private final UserAuthRole authRole;

}
