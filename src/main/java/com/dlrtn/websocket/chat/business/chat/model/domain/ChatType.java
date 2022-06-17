package com.dlrtn.websocket.chat.business.chat.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatType {

    SELF("개인"),
    PUBLIC("공개"),
    ;

    private final String description;

}
