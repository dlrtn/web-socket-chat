package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
