package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.aop.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/users/{userId}/self/{chatId}/message")
    public void selfMessage(ChatMessage message,
                            @DestinationVariable String chatId) {
        template.convertAndSend("/sub/chats/" + chatId, message);
    }

    @MessageMapping(value = "/users/{userId}/private/{chatId}/message")
    public void privateMessage(ChatMessage message,
                               @DestinationVariable String chatId) {
        template.convertAndSend("/sub/chats/" + chatId, message);
    }

    @MessageMapping(value = "/users/{userId}/chats/{chatId}/join")
    public void groupJoin(@SessionUser User sessionUser,
                          @DestinationVariable String chatId) {
        template.convertAndSend("/sub/chats/" + chatId, sessionUser.getRealName() + "님이 채팅방에 참여하였습니다.");
    }

    @MessageMapping(value = "/users/{userId}/chats/{chatId}/message")
    public void groupMessage(ChatMessage message,
                             @DestinationVariable String chatId) {
        template.convertAndSend("/sub/chats/" + chatId, message);
    }

}
