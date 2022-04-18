package com.dlrtn.websocket.chat.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CommonError {

    private final int code;

    private final String error;

    private final String message;

}
