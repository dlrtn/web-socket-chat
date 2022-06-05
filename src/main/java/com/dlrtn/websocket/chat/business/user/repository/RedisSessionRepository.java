package com.dlrtn.websocket.chat.business.user.repository;

import com.dlrtn.websocket.chat.config.redisdb.RedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisSessionRepository extends CrudRepository<RedisEntity, String> {

}
