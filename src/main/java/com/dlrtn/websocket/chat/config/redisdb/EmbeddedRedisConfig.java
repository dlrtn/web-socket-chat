package com.dlrtn.websocket.chat.config.redisdb;

import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.config.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EmbeddedRedisConfig {

    private final RedisProperties redisProperties;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        try {
            redisServer = new RedisServer(redisProperties.getPort());
            redisServer.start();
        } catch (Exception e) {
            throw new CommonException(String.format("Failed to Start Embedded Redis Server, error code : %s", e.getMessage()));
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
