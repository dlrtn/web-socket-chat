package com.dlrtn.websocket.chat.business.user.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
