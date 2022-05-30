package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chats/{chatId}/enter")
    public void enter(ChatMessage message) {
        message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getChatId(), message);
    }

    @MessageMapping(value = "/chats/{chatId}/message")
    public void message(ChatMessage message) {
        template.convertAndSend("/sub/chat/room/" + message.getChatId(), message);
    }

}
