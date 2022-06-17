package com.dlrtn.websocket.chat.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedisProperties {

    private String host;

    private int port;

}
