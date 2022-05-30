package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.application.ChatService;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.ChangeChatRoomRespnose;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomResponse;
import com.dlrtn.websocket.chat.business.chat.model.payload.ExitChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/users/{userId}/chats")
    public CreateChatRoomResponse createChatRoom(@Valid @RequestBody CreateChatRoomRequest createChatRoomRequest) {
        return chatService.createChatRoom(createChatRoomRequest);
    }

    @GetMapping("/users/{userId}/chats/{chatsId}")
    public ChatRoom findChatRoom(@PathVariable String chatsId) {
        return chatService.getChatRoom(chatsId);
    }

    @DeleteMapping("/users/{userId}/chats/{chatsId}")
    public ExitChatRoomResponse exitChatRoom(@PathVariable String chatsId) {
        return chatService.exitChatRoom(chatsId);
    }

    @PutMapping("/users/{userId}/chats/{chatsId}")
    public ChangeChatRoomRespnose changeChatRoom(@PathVariable String chatsId, String roomName) {
        return chatService.changeChatRoom(chatsId, roomName);
    }

}
