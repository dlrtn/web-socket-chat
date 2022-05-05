package com.dlrtn.websocket.chat.model.payload;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
