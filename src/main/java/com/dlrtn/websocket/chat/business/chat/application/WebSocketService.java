package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import com.dlrtn.websocket.chat.business.chat.model.domain.MessageType;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
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

    @Async
    public void sendPrivateJoinMessage(User user, String chatId) {
        ChatMessage message = ChatMessage.builder()
                .sender(user.getUsername())
                .chatId(chatId)
                .message(user.getRealName() + "님이 채팅방을 개설하셨습니다.")
                .type(MessageType.NOTIFICATION)
                .build();

        sendMessageToChatSubscribers(message);
    }

    @Async
    public void sendGroupJoinMessage(User user, String chatId) {
        ChatMessage message = ChatMessage.builder()
                .sender(user.getUsername())
                .chatId(chatId)
                .message(user.getRealName() + "님이 채팅방에 참여하였습니다.")
                .type(MessageType.NOTIFICATION)
                .build();

        sendMessageToChatSubscribers(message);
    }

    private void sendMessageToChatSubscribers(ChatMessage message) {
        template.convertAndSend("/sub/chats/" + message.getChatId(), message);
    }

    private void sendPrivateMessageToChatSubscribers(ChatMessage message) { //일단 필요한 메소드인진 모르겠음!
        sendMessageToChatSubscribers(message);
    }

}
