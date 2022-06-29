package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/users/{userId}/chats/{chatId}/message")
    public void selfMessage(ChatMessage message,
                            @DestinationVariable String userId,
                            @DestinationVariable String chatId) {
        template.convertAndSend("/sub", message);
    }

    @MessageMapping(value = "/users/{userId}/chats/{chatId}/join")
    public void groupJoin(ChatMessage message,
                          @DestinationVariable String userId,
                          @DestinationVariable String chatId) {
        template.convertAndSend("/sub", message.getSender() + "님이 채팅방에 참여하였습니다.");
    }

    @MessageMapping(value = "/users/{userId}/chats/{chatId}/message")
    public void groupMessage(ChatMessage message,
                             @DestinationVariable String userId,
                             @DestinationVariable String chatId) {
        template.convertAndSend("/sub", message);
    }

}
