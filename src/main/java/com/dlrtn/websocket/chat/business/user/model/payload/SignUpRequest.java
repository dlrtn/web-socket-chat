package com.dlrtn.websocket.chat.business.user.model.payload;

import com.dlrtn.websocket.chat.business.user.model.UserAuthRole;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String realName;

    @NotNull
    private UserAuthRole authRole;

}
