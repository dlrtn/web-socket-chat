package com.dlrtn.websocket.chat.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
public class User {
	private final long userNo;
	private final String username;
	private final String password;
	private final String realName;
	private final String authRole;
	private final String appendAt;
	private final String updateAt;
}