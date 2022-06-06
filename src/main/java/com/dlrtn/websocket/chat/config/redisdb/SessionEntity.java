package com.dlrtn.websocket.chat.config.redisdb;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("SessionRepository")
@Getter
@Builder
@ToString
public class SessionEntity implements Serializable {

    @Id
    private String sessionId;

    private User sessionUser;

}
