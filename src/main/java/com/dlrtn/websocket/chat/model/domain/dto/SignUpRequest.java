package com.dlrtn.websocket.chat.model.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequest {

    private String userId;
    private String password;
    private String realName;
    private String authRole;

}