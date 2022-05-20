package com.dlrtn.websocket.chat.controller;

import com.dlrtn.websocket.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dlrtn.websocket.chat.model.PagePathConstants.CHAT;

@RequiredArgsConstructor
@RestController
@RequestMapping(CHAT)
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

}
