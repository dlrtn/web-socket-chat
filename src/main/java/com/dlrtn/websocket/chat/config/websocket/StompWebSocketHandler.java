package com.dlrtn.websocket.chat.config.websocket;

import com.dlrtn.websocket.chat.business.user.repository.UserSessionRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@RequiredArgsConstructor
public class StompWebSocketHandler implements ChannelInterceptor {

    private UserSessionRepository sessionRepository;

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        switch (accessor.getCommand()) {

            case CONNECT:
                if (sessionRepository.existsById(sessionId)) {
                    String userId = sessionRepository
                            .findById(sessionId)
                            .orElseThrow(() -> new CommonException("User not exists in session"))
                            .getSessionUser()
                            .getUsername();
                    break;
                }
                return null;

            case SUBSCRIBE:
                String destination = accessor.getDestination();
                //destination is chat room path
                //getDestination

                break;

            default:

                return null;

        }

        return message;
    }

    //todo subscriber destination 관련 로직 구현
    //todo chatroom이 현재 mysql db에 저장되어 있는데 세션으로?

}
