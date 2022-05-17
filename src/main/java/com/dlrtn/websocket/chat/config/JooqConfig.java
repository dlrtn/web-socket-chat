package com.dlrtn.websocket.chat.config;

import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

    public DefaultDSLContext dslContext(org.jooq.Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }

}
