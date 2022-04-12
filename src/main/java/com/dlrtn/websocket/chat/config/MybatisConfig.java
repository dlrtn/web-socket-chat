package com.dlrtn.websocket.chat.config;

import com.dlrtn.websocket.chat.handler.LocalDateTimeVarCharTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public LocalDateTimeVarCharTypeHandler localDateTimeVarCharTypeHandler() {
        return new LocalDateTimeVarCharTypeHandler();
    }

}
