package com.dlrtn.websocket.chat.controller;

import com.dlrtn.websocket.chat.model.domain.ChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dlrtn.websocket.chat.model.PagePathConstants.*;

@Controller
@RequestMapping(CHAT)
public class ChatController {

    private final SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/join")
    public void join(ChatMessage message) {
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @MessageMapping("/message")
    public void message(ChatMessage message) {
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

}
