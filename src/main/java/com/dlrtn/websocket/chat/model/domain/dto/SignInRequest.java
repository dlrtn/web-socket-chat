package com.dlrtn.websocket.chat.model.domain.dto;

import lombok.Getter;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Getter
@Builder(toBuilder = true)
public class SignInRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

}
