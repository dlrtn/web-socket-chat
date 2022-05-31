package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.application.ChatRoomService;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.ChangeChatRoomResponse;
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

    private final ChatRoomService ChatRoomService;

    @PostMapping("/users/{userId}/chats")
    public CreateChatRoomResponse createChatRoom(@PathVariable String userId,
                                                 @Valid @RequestBody CreateChatRoomRequest createChatRoomRequest) {
        return ChatRoomService.createChatRoom(userId, createChatRoomRequest);
    }

    @GetMapping("/users/{userId}/chats/{chatsId}")
    public ChatRoom findChatRoom(@PathVariable String userId,
                                 @PathVariable String chatsId) {
        return ChatRoomService.getChatRoom(userId, chatsId);
    }

    @DeleteMapping("/users/{userId}/chats/{chatsId}")
    public ExitChatRoomResponse exitChatRoom(@PathVariable String userId,
                                             @PathVariable String chatsId) {
        return ChatRoomService.exitChatRoom(userId, chatsId);
    }

    @PutMapping("/users/{userId}/chats/{chatsId}")
    public ChangeChatRoomResponse changeChatRoom(@PathVariable String userId,
                                                 @PathVariable String chatsId,
                                                 String roomName) {
        return ChatRoomService.changeChatRoom(userId, chatsId, roomName);
    }

}
