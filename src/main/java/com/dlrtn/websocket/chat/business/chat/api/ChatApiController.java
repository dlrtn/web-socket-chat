package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.ExitRoomRequest;
import com.dlrtn.websocket.chat.business.chat.application.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dlrtn.websocket.chat.model.PagePathConstants.CHAT;

@RequiredArgsConstructor
@RestController
@RequestMapping(CHAT)
public class ChatApiController {

    @Autowired
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @PostMapping("/delete")
    public boolean exitRoom(@Valid @RequestBody ExitRoomRequest exitRoomRequest) {
        return chatService.delete(exitRoomRequest.getRoomId());
    }

}
