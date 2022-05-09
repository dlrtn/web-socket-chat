package com.dlrtn.websocket.chat.model.domain;

import com.dlrtn.websocket.chat.model.UserAuthRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@ToString
public class User {

    private final long userNo;

    private final String username;

    private final String password;

    private final String realName;

    private final UserAuthRole authRole;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;



}
