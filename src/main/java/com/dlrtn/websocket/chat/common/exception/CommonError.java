package com.dlrtn.websocket.chat.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
public class CommonError {

    private final int code;

    private final String error;

    private final String message;

}
