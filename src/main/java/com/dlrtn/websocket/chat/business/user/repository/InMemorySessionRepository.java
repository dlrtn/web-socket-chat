package com.dlrtn.websocket.chat.business.user.repository;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class InMemorySessionRepository {

    private final Map<String, User> sessions = new ConcurrentHashMap<>();

    public void put(String sessionId, User user) {
        sessions.put(sessionId, user);
    }

    public User get(String sessionId) {
        return sessions.get(sessionId);
    }

    public boolean exists(String sessionId) {
        return StringUtils.isNotEmpty(sessionId) && sessions.containsKey(sessionId);
    }

}
