package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.application.WebSocketService;
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

    private final WebSocketService webSocketService;

    @MessageMapping(value = "/users/{userId}/self/{chatId}/message")
    public void selfMessage(ChatMessage message,
                            @SessionUser User sessionUser,
                            @DestinationVariable String chatId) {
        webSocketService.sendSelfMessage(sessionUser, message);
    }

    @MessageMapping(value = "/users/{userId}/private/{chatId}/message")
    public void privateMessage(@SessionUser User sessionUser,
                               @DestinationVariable String chatId) {
        webSocketService.sendPrivateJoinMessage(sessionUser, chatId);
    }

    @MessageMapping(value = "/users/{userId}/private/{chatId}/message")
    public void privateMessage(ChatMessage message,
                               @SessionUser User sessionUser,
                               @DestinationVariable String chatId) {
        webSocketService.sendPrivateMessage(sessionUser, message);
    }

    @MessageMapping(value = "/users/{userId}/chats/{chatId}/join")
    public void groupJoin(@SessionUser User sessionUser,
                          @DestinationVariable String chatId) {
        webSocketService.sendGroupJoinMessage(sessionUser, chatId);
    }

    @MessageMapping(value = "/users/{userId}/chats/{chatId}/message")
    public void groupMessage(ChatMessage message,
                             @SessionUser User sessionUser,
                             @DestinationVariable String chatId) {
        webSocketService.sendGroupMessage(sessionUser, message);
    }

}
