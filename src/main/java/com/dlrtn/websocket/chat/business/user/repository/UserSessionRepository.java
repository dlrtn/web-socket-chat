package com.dlrtn.websocket.chat.business.user.repository;

import com.dlrtn.websocket.chat.config.redisdb.SessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserSessionRepository extends CrudRepository<SessionEntity, String> {

}
