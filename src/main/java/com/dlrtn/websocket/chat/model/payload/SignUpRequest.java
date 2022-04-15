package com.dlrtn.websocket.chat.model.payload;

import com.dlrtn.websocket.chat.model.UserAuthRole;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class SignUpRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String realName;

    @NotBlank
    private UserAuthRole authRole;

}
