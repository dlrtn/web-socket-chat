package com.dlrtn.websocket.chat.model.domain.dto;

import lombok.*;

@Getter
@Builder(toBuilder = true)
public class SignUpRequest {

    private String userId;
    private String password;
    private String realName;
    private String authRole;

}
