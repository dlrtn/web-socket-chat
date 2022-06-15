package com.dlrtn.websocket.chat.business.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInformation {

    private String userId;

    private String realName;

}