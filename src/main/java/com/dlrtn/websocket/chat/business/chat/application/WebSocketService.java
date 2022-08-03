package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;

public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    @Async
    public void sendSelfMessage(User user, ChatMessage message) {
        sendMessageToChatSubscribers(message);
    }

    @Async
    public void sendPrivateMessage(User user, ChatMessage message) {
        sendMessageToChatSubscribers(message);
    }

    @Async
    public void sendGroupMessage(User user, ChatMessage message) {
        sendMessageToChatSubscribers(message);
    }

    private void sendMessageToChatSubscribers(ChatMessage message) {
        template.convertAndSend(message.getChatId(), message);
    }

    private void sendPrivateMessageToChatSubscribers(ChatMessage message) {
        sendMessageToChatSubscribers(message);
    }

}
