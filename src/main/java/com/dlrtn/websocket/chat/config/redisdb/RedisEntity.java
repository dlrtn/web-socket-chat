package com.dlrtn.websocket.chat.config.redisdb;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("sessionRepo")
@Getter
@Builder
@ToString
public class RedisEntity implements Serializable {

    private static final long serialVersionUID = 1370692830319429806L;

    @Id
    private String sessionId;

    private User sessionUser;

}
